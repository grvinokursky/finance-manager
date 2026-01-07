package org.example.containers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import org.example.models.UserModel;
import org.example.models.WalletModel;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataContainer {
    private final String pathDataFile = "data/data.json";
    private final ObjectMapper mapper;

    private List<UserModel> users;

    public DataContainer() {
        users = new ArrayList<>();
        mapper = new ObjectMapper();
        mapper.findAndRegisterModules(); // для LocalDate/Time и т.п.
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // красивый отступ
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<WalletModel> getWallets() {
        return users.stream()
                .map(UserModel::getWallet)
                .collect(Collectors.toList());
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    @PostConstruct
    public void readData() {
        try {
            users = mapper.readValue(Paths.get(pathDataFile).toFile(), new TypeReference<List<UserModel>>() {});
        } catch (Exception e) {
            users = new ArrayList<>();
        }
    }

    public void saveData() {
        try {
            mapper.writeValue(Paths.get(pathDataFile).toFile(), users);
        } catch (Exception e) {
            System.out.printf("Не удалось выполнить сохранение данных. %s%n", e.getMessage());
        }
    }
}
