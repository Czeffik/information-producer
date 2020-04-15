package com.trzewik.information.producer.infrastructure.rest;

import com.trzewik.information.producer.domain.information.Information;
import com.trzewik.information.producer.domain.information.InformationReceiver;
import com.trzewik.information.producer.domain.information.Person;

import java.util.ArrayList;

public class InformationReceiverImpl implements InformationReceiver {
    @Override
    public Information getInformation(String id) {
        return new Information("", "", "", new Person("", ""), new ArrayList<>());
    }
}
