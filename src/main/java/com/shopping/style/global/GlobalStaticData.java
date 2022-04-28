package com.shopping.style.global;

import java.util.ArrayList;
import java.util.List;

import com.shopping.style.models.Product;

public class GlobalStaticData {
public static List<Product> cart;
static {
	cart=new ArrayList<Product>();
}
}
