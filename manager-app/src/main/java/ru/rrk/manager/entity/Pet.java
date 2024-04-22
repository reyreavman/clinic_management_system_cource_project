package ru.rrk.manager.entity;

import java.util.Date;

public record Pet(int id, int clientId, String name, int typeId, int breedId, int genderId, Date birthday,
                  int labelId) {
}
