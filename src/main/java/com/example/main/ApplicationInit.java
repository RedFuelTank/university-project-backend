package com.example.main;

import com.example.main.config.security.jwt.JwtTokenProvider;
import com.example.main.config.security.users.UserRole;
import com.example.main.model.Advertisement;
import com.example.main.model.User;
import com.example.main.repository.AdvertisementRepository;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationInit implements CommandLineRunner {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (advertisementRepository.getAllRequests().isEmpty()) {
            List<Advertisement> advertisements = List.of(
                    new Advertisement("Walk with my dog", "Walk with my dog for 30 euro pls", 1L, 0, 0, "", "25-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("I am able to walk with your dog", "I can walk with your dog for 15 euro", 1L, 0, 0, "", "25-10-2022", Advertisement.Type.OFFER),
                    new Advertisement("Ремонт на кухне", "Срочно требуется какой-нибудь строитель, который может сдлеать ремонт на кухне. В частности поставить новую плиту и микроволновку, Цена за работу 50 евро", 1L, 0, 0, "", "25-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Тиммейты дорогие делайте работу", "БЫСТРО СДЕЛАЛИ СВОИ ФРОНТЕНДЫ И БЭКЕНДЫ ПОРТКИ СВОИ ПОДОБРАЛИ, Я ВАМ ПЛАТИТЬ НЕ БУДУ, МЫ РАБОТАЕМ ЗА ИДЕЮ", 2L, 0, 0, "", "25-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-4 - AUTO DEPLOY WORKS", "blank", 2L, 0, 0, "", "25-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-5", "blank", 2L, 0, 0, "", "10-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-6", "blank", 2L, 0, 0, "", "15-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-7", "blank", 2L, 0, 0, "", "28-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-8", "blank", 2L, 0, 0, "", "06-10-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-9 TEST", "blank", 2L, 0, 0, "", "25-01-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-10", "blank", 2L, 0, 0, "", "25-03-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-11", "blank", 2L, 0, 0, "", "25-07-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-12", "blank", 2L, 0, 0, "", "25-11-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-13", "blank", 2L, 0, 0, "", "25-08-2022", Advertisement.Type.REQUEST),
                    new Advertisement("Request-14", "blank", 2L, 0, 0, "", "25-10-2023", Advertisement.Type.REQUEST),
                    new Advertisement("Request-15", "blank", 2L, 0, 0, "", "25-10-2026", Advertisement.Type.REQUEST),
                    new Advertisement("Request-16", "blank", 2L, 0, 0, "", "25-10-2025", Advertisement.Type.REQUEST),
                    new Advertisement("Request-17", "blank", 2L, 0, 0, "", "25-10-2030", Advertisement.Type.REQUEST),
                    new Advertisement("Request-18", "blank", 2L, 0, 0, "", "25-10-2027", Advertisement.Type.REQUEST));
            advertisementRepository.saveAll(advertisements);

            User user1 = new User("danila", passwordEncoder.encode("danila123"),
                    "danila.romanof@gmail.com", "Danila", "Romanov", "55503565");
            user1.setRole(UserRole.ADMIN);

            User user2 = new User("kilril", passwordEncoder.encode("kilril123"),
                    "kilril.timofejev@gmail.com", "Kirill", "Timofejev", "55555555");
            user2.setRole(UserRole.USER);
            List<User> users = List.of(user1, user2);
            userRepository.saveAll(users);
        }
    }
}

