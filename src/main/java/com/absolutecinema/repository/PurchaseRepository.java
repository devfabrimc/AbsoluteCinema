package com.absolutecinema.repository;

import com.absolutecinema.model.Purchase;
import com.absolutecinema.utils.Paths;
import com.absolutecinema.utils.TxtFileManager;

import java.util.ArrayList;
import java.util.List;

public class PurchaseRepository implements Repository<Purchase> {
    public static final String filePath = Paths.PURCHASE_REPOSITORY;
    public final TxtFileManager fileManager = new TxtFileManager();

    @Override
    public List<Purchase> findAll() {
        List<Purchase> purchases = new ArrayList<>();

        for (String line : fileManager.readLines(filePath)) {
            purchases.add(Purchase.printformat(line));
        }

        return purchases;
    }

    @Override
    public Purchase findById(String id) {
        for (Purchase purchase : findAll()) {
            if (purchase.getId().equals(id)) {
                return purchase;
            }
        }

        return null;
    }

    @Override
    public void save(Purchase purchase) {
        fileManager.appendLine(filePath, purchase.toString());
    }

    @Override
    public void update(Purchase purchase) {
        List<Purchase> purchases = findAll();

        for (int i = 0; i < purchases.size(); i++) {
            if (purchases.get(i).getId().equals(purchase.getId())) {
                purchases.set(i, purchase);
                break;
            }
        }

        writeAll(purchases);
    }

    @Override
    public void delete(String id) {
        List<Purchase> purchases = findAll();

        purchases.removeIf(purchase -> purchase.getId().equals(id));
        writeAll(purchases);
    }

    public List<Purchase> findByUserId(String userId) {
        List<Purchase> result = new ArrayList<>();

        for (Purchase purchase : findAll()) {
            if (purchase.getUserId().equals(userId)) {
                result.add(purchase);
            }
        }
        return result;
    }

    public String getLastId() {
        List<Purchase> purchases = findAll();

        if (purchases.isEmpty()) {
            return "PUR000";
        }
        return purchases.get(purchases.size() - 1).getId();
    }

    private void writeAll(List<Purchase> purchases){
        List<String> lines = new ArrayList<>();

        for(Purchase purchase : purchases){
            lines.add(purchase.toString());
        }

        fileManager.writeLines(filePath, lines);
    }
}
