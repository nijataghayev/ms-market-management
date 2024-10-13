package com.example.marketmanagement.service

import com.example.marketmanagement.dao.entity.UserProfileEntity
import com.example.marketmanagement.dao.repository.UserCredentialsRepository
import com.example.marketmanagement.dao.repository.UserProfileRepository
import com.example.marketmanagement.mapper.UserCredentialsMapper
import com.example.marketmanagement.mapper.UserProfileMapper
import com.example.marketmanagement.model.UserProfileResDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

/**
 * @author: nijataghayev
 */
class UserServiceTest extends Specification {
    private EnhancedRandom random
    private UserService userService
    private UserCredentialsRepository credentialsRepository
    private UserProfileRepository profileRepository
    private UserCredentialsMapper credentialsMapper
    private UserProfileMapper profileMapper
    private PasswordEncoder passwordEncoder
    private JavaMailSender mailSender

    void setup() {
        random = EnhancedRandomBuilder.aNewEnhancedRandom()
        credentialsRepository = Mock()
        profileRepository = Mock()
        credentialsMapper = Mock()
        profileMapper = Mock()
        passwordEncoder = Mock()
        mailSender = Mock()
        userService = new UserService(
                credentialsRepository,
                profileRepository,
                credentialsMapper,
                profileMapper,
                passwordEncoder,
                mailSender
        )
    }

    def "GetAllUsers"() {
        given:
        def userEntities = random.objects(UserProfileEntity, 5).toList()
        def userDtos = userEntities.collect { random.nextObject(UserProfileResDto) }
        profileRepository.findAll() >> userEntities
        userEntities.eachWithIndex { entity, idx ->
            profileMapper.mapToDtoRes(entity) >> userDtos[idx]
        }

        when:
        def result = userService.getAllUsers()

        then:
        result.size() == userDtos.size()
        result == userDtos
    }

    def "GetUser"() {
        given:
        def profileEntity = random.nextObject(UserProfileEntity)
        def profileDto = random.nextObject(UserProfileResDto)

        when:
        def result = userService.getUser(userId);

        then:
        1 * profileRepository.findById(userId) >> Optional.of(profileEntity)
        1 * profileMapper.mapToDtoRes(profileEntity) >> profileDto

        result == profileDto

        where:
        userId << [1, 2, 5]
    }
}
