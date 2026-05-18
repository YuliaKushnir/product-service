package org.example.productservice.dto;

import org.example.productservice.data.enums.Color;
import org.example.productservice.data.enums.Gender;

import java.util.List;

public interface ProductFilter {
    List<String> getCategories();
    List<Color> getColors();
    Gender getGender();
    String getMaterial();
}
