package com.alpha.core.core.models;

import com.alpha.core.core.beans.MultipleMultifieldHelper;

import java.util.List;
import java.util.Map;

public interface MultipleMultifield {
    List<Map<String, Object>> getBooksDetails();
    List<MultipleMultifieldHelper>getBooksDetailsBean();
}
