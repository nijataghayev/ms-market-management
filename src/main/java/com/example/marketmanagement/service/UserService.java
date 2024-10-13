package com.example.marketmanagement.service;

import com.example.marketmanagement.dao.entity.CardEntity;
import com.example.marketmanagement.dao.entity.RoleEntity;
import com.example.marketmanagement.dao.entity.UserCredentialEntity;
import com.example.marketmanagement.dao.entity.UserProfileEntity;
import com.example.marketmanagement.dao.repository.CardRepository;
import com.example.marketmanagement.dao.repository.UserCredentialsRepository;
import com.example.marketmanagement.dao.repository.UserProfileRepository;
import com.example.marketmanagement.exception.NotFoundException;
import com.example.marketmanagement.mapper.UserCredentialsMapper;
import com.example.marketmanagement.mapper.UserProfileMapper;
import com.example.marketmanagement.model.UserDto;
import com.example.marketmanagement.model.UserOtpData;
import com.example.marketmanagement.model.UserProfileReqDto;
import com.example.marketmanagement.model.UserProfileResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * @author: nijataghayev
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserCredentialsRepository credentialsRepository;
    private final UserProfileRepository profileRepository;
    private final UserCredentialsMapper credentialsMapper;
    private final UserProfileMapper profileMapper;
    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, UserOtpData> otpStore = new HashMap<>();
    private final Map<String, UserDto> tempUserStore = new HashMap<>();
    private final JavaMailSender mailSender;

    public void register(UserDto userDto) {
        log.info("ActionLog.register.start userCredentials {}", userDto);
        Optional<UserCredentialEntity> existingUser = credentialsRepository.findByUsername(userDto.getCredentialsDto().getUsername());

        if (existingUser.isPresent()) {
            System.out.println("Username already exists. Please choose a different username.");
        }

        String otp = generateOtpCode();
        LocalDateTime otpExpiry = LocalDateTime.now().plusMinutes(3);
        otpStore.put(userDto.getCredentialsDto().getEmail(), new UserOtpData(otp, otpExpiry));
        tempUserStore.put(userDto.getCredentialsDto().getEmail(), userDto);

        sendOtpToEmail(userDto.getCredentialsDto().getEmail(), otp);

        log.info("ActionLog.register.end userCredentials {}", userDto);
    }

    public boolean verifyOtp(String email, String otp) {
        UserOtpData userOtpData = otpStore.get(email);
        if (userOtpData == null || userOtpData.getOtpExpiry().isBefore(LocalDateTime.now())) {
            otpStore.remove(email);
            tempUserStore.remove(email);
            return false;
        }

        if (userOtpData.getOtp().equals(otp)) {
            UserDto userDto = tempUserStore.get(email);
            if (userDto == null) {
                return false;
            }

            var profileEntity = profileMapper.mapToEntityReq(userDto.getProfileDto());
            var credentialsEntity = credentialsMapper.mapToEntity(userDto.getCredentialsDto());

            profileEntity.setCredential(credentialsEntity);
            credentialsEntity.setUserProfile(profileEntity);
            credentialsEntity.setPassword(passwordEncoder.encode(userDto.getCredentialsDto().getPassword()));
            RoleEntity roleEntity = new RoleEntity(null, "ROLE_USER", credentialsEntity);
            credentialsEntity.setRoles(List.of(roleEntity));

            profileRepository.save(profileEntity);
            createCardForUser(profileEntity);

            otpStore.remove(email);
            tempUserStore.remove(email);

            return true;
        }
        return false;
    }

    public List<UserProfileResDto> getAllUsers() {
        log.info("ActionLog.getAllUsers.start");
        List<UserProfileEntity> userEntityList = profileRepository.findAll();
        List<UserProfileResDto> userDtoList = userEntityList.stream()
                .map(profileMapper::mapToDtoRes)
                .toList();
        log.info("ActionLog.getAllUsers.end");

        return userDtoList;
    }

    public UserProfileResDto getUser(Long userId) {
        log.info("ActionLog.getUser.start userId {}", userId);
        UserProfileEntity userProfileEntity = profileRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "USER_NOT_FOUND",
                        String.format("ActionLog.getUser.id %s not found", userId)
                ));

        UserProfileResDto userProfileDto = profileMapper.mapToDtoRes(userProfileEntity);
        log.info("ActionLog.getUser.end userId {}", userId);

        return userProfileDto;
    }

    public void updateUser(Long userId, UserProfileReqDto userDto) {
        UserProfileEntity existingUser = profileRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        "USER_NOT_FOUND",
                        String.format("ActionLog.updateUser.id %s not found", userId)
                ));

        UserProfileEntity updateUser = profileMapper.mapToEntityReq(userDto);

        existingUser.setName(updateUser.getName());
        existingUser.setSurname(updateUser.getSurname());
        existingUser.setAddress(updateUser.getAddress());
        existingUser.setPhone(updateUser.getPhone());

        if (updateUser.getCredential() != null) {
            UserCredentialEntity updatedCredentials = updateUser.getCredential();
            UserCredentialEntity existingCredentials = existingUser.getCredential();

            if (existingCredentials == null) {
                existingUser.setCredential(updatedCredentials);
            } else {
                existingCredentials.setEmail(updatedCredentials.getEmail());
                existingCredentials.setUsername(updatedCredentials.getUsername());
                credentialsRepository.save(existingCredentials); // Explicitly save updated credentials
            }

            profileRepository.save(existingUser);
        }
    }

    public void sendDiscountEmail(String email, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Happy Birthday ❤️");
        message.setText("Əziz " + name + ", ad gününüzə özəl bütün məhsullara 20% endirim əldə etdiniz");

        mailSender.send(message);
    }

    private void sendOtpToEmail(String email, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otpCode);

        mailSender.send(message);
    }

    private String generateOtpCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    private void createCardForUser(UserProfileEntity userProfile) {
        CardEntity card = new CardEntity();
        card.setUser(userProfile);
        card.setAmount(0.0);

        cardRepository.save(card);
    }


    public List<UserProfileEntity> getUsersWithBirthdayToday() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        return profileRepository.findByBirthDate(month, day);
    }
}
