package org.example;

import java.util.List;

public class ProductService {

    ProductDao pd;

    /**
     * 処理：Connectionインスタンスを作成し、ProductDao型のフィールドを初期化する
     */
    public ProductService() {
        DBUtil db = new DBUtil();
        ProductDao pd = new ProductDao(db.getConnection());
        this.pd = pd;
    }

    /**
     * ProductDaoのfindByIdメソッドを呼び出し、取得結果を返す
     * 結果がnullの場合はProductNotFoundExceptionの例外をスローする
     * @param id
     * @return
     */
    public ProductRecord findById(int id){
        return this.pd.findById(id);
    }

    /**
     * ProductDaoのfindByNameメソッドを呼び出し、結果を返す。
     * @param str
     * @return
     */
    public List<ProductRecord> findByName(String str){
        return this.pd.findByName(str);
    }

    /**
     * 引数で受け取った値でproductsテーブルに対してレコードをインサートする。
     * 処理件数を戻り値で返す。
     * @param pr
     * @return
     */
    public int insert(ProductRecord pr){
        int counts = this.pd.insert(pr);
        ProductRecord[] prs = {pr};
        if(counts == prs.length){
            return counts;
        }else {
            throw new ProductNotFoundException();
        }
    }

    /**
     * ProductDaoのupdateメソッドを呼び出し、結果を返す。
     * @param pr
     * @return
     */
    public int update(ProductRecord pr){
        int counts = this.pd.update(pr);
        ProductRecord[] prs = {pr};
        if(counts == prs.length){
            return counts;
        }else {
            throw new ProductNotFoundException();
        }
    }

    /**
     * ProductDaoのdeleteメソッドを呼び出し、結果を返す
     * @param id
     * @return
     */
    public int delete(int id){
        int counts = this.pd.delete(id);
        if(counts == 1){
            return counts;
        }else {
            throw new ProductNotFoundException();
        }
    }
}
