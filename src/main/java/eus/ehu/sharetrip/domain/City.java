package eus.ehu.sharetrip.domain;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.List;
import java.util.Vector;

@Entity
public class City {
    @Id
    private String name;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
