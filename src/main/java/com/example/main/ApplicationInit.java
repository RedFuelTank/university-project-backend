package com.example.main;

import com.example.main.model.Advertisement;
import com.example.main.model.User;
import com.example.main.repository.AdvertisementRepository;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationInit implements CommandLineRunner {

  @Autowired
  private AdvertisementRepository advertisementRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void run(String... args) {
    List<Advertisement> advertisements = List.of(
            new Advertisement("Walk with my dog", "Walk with my dog for 30 euro pls", 1, 0, 0, "", Advertisement.Type.REQUEST),
            new Advertisement("I am able to walk with your dog", "I can walk with your dog for 15 euro", 1, 0, 0, "", Advertisement.Type.OFFER),
            new Advertisement("Ремонт на кухне", "Срочно требуется какой-нибудь строитель, который может сдлеать ремонт на кухне. В частности поставить новую плиту и микроволновку, Цена за работу 50 евро", 1, 0, 0, "", Advertisement.Type.REQUEST),
            new Advertisement("Тиммейты дорогие делайте работу", "БЫСТРО СДЕЛАЛИ СВОИ ФРОНТЕНДЫ И БЭКЕНДЫ ПОРТКИ СВОИ ПОДОБРАЛИ, Я ВАМ ПЛАТИТЬ НЕ БУДУ, МЫ РАБОТАЕМ ЗА ИДЕЮ", 2, 0, 0, "", Advertisement.Type.REQUEST));
    advertisementRepository.saveAll(advertisements);

    List<User> users = List.of(new User("RedFuelTank", "12345", "danila.romanof@gmail.com",
      "Danila", "Romanov", "55503565"),
            new User("kilril", "123456789", "kilril.timofejev@gmail.com", "Kirill", "Timofejev", "55555555"));
    userRepository.saveAll(users);

  }
}

