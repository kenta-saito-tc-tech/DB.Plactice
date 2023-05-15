package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private Connection connect;

    /**
     * コンストラクタ
     *
     * @param connect データベースのコネクション
     */
    public ProductDao(Connection connect) {
        this.connect = connect;
    }

    /**
     * int型のidを引数に取り、idでproductsテーブルを検索します。
     * ヒットしたレコードからProductRecordのオブジェクトを作成して返す。
     * レコードが存在しない場合はnullを返す。
     * @param id
     * @return
     */
    public ProductRecord findById(int id) {
        final var SQL = "SELECT id, name, price FROM products WHERE id = ?";

        try (PreparedStatement stmt = this.connect.prepareStatement(SQL)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ProductRecord product = new ProductRecord
                        (rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 引数の文字列を元にnameに対して曖昧検索を行い、ヒットした件数分のリストを返す。
     * @param str
     * @return
     */
    public List<ProductRecord> findByName(String str) {
        final var SQL = "SELECT id, name, price FROM products WHERE name LIKE ?";

        var list = new ArrayList<ProductRecord>();

        try (PreparedStatement stmt = this.connect.prepareStatement(SQL)) {
            stmt.setString(1, "%" +str + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                var product = new ProductRecord
                        (rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
                list.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 引数で受け取った値でproductsテーブルに対してレコードをインサートする
     * 処理件数を戻り値で返す
     *
     * @param pr
     * @return
     */
    public int insert(ProductRecord pr) {
        int counts = 0;
        String SQL = "INSERT INTO products (id, name, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = this.connect.prepareStatement(SQL)) {
            this.connect.setAutoCommit(false);
            ProductRecord[] prs = {pr};
            for (var product : prs) {
                stmt.setInt(1, product.id());
                stmt.setString(2, product.name());
                stmt.setInt(3, product.price());
                counts++;
            }
            stmt.executeUpdate();
            this.connect.commit();
            return counts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 引数で受け取ったProductRecordのidをwhere句で指定し、nameとpriceをupdateする
     * 処理件数を戻り値で返す
     *
     * @param pr
     * @return
     */
    public int update(ProductRecord pr) {
        int counts = 0;
        String SQL = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        try (PreparedStatement stmt = this.connect.prepareStatement(SQL)) {
            this.connect.setAutoCommit(false);
            ProductRecord[] prs = {pr};
            for (var product : prs) {
                stmt.setString(1, product.name());
                stmt.setInt(2, product.price());
                stmt.setInt(3, product.id());
                counts++;
            }
            stmt.executeUpdate();
            this.connect.commit();
            return counts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 引数で受け取ったint型の変数をproductsのidとして指定し、deleteする
     * 処理件数を戻り値で返す
     *
     * @param id
     * @return
     */
    public int delete(int id) {
        int counts = 0;
        String SQL = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement stmt = this.connect.prepareStatement(SQL)) {
            this.connect.setAutoCommit(false);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            this.connect.commit();
            counts++;
            return counts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
