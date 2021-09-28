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
    List<Advertisement> advertisements = List.of(new Advertisement("Walk with my dog", "Walk with my dog for 30 euro pls", 2, Advertisement.Type.REQUEST),
      new Advertisement("I am able to walk with your dog", "I can walk with your dog for 15 euro", 3, Advertisement.Type.OFFER));
    advertisementRepository.saveAll(advertisements);
    List<User> users = List.of(new User("RedFuelTank", "12345", "danila.romanof@gmail.com",
      "Danila", "Romanov", "55503565"));
    userRepository.saveAll(users);

  }
}

