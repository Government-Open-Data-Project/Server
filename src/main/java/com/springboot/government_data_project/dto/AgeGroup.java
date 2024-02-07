package com.springboot.government_data_project.dto;

public enum AgeGroup {
    TWENTIES, THIRTIES, FORTIES, FIFTIES, SIXTIES, SEVENTIES;

    @Override
    public String toString(){
        return super.toString().toLowerCase();
    }
}
