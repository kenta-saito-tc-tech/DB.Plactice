package org.example;

public class Main {
    public static void main(String[] args) {
        ProductService ps = new ProductService();
        //select文（ID検索）
        System.out.println(ps.findById(101));
        //select文（文字列検索）
        System.out.println(ps.findByName("消"));
        //insert文
        //ProductRecord pr = new ProductRecord(104, "水筒", 2300);
        //System.out.println(ps.insert(pr));
        //update文
        ProductRecord pr2 = new ProductRecord(103, "new地球儀", 7600);
        System.out.println(ps.update(pr2));
        //delete文
        System.out.println(ps.delete(104));
    }
}