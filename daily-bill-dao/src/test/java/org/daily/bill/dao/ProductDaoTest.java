package org.daily.bill.dao;

import org.daily.bill.api.dao.ProductDao;
import org.daily.bill.domain.Product;
import org.daily.bill.utils.TestEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.testng.CommandLineArgs.TEST_NAMES;

/**
 * Created by vano on 31.8.16.
 */
public class ProductDaoTest extends AbstractDaoTest<Long, Product, ProductDao> {

    private static final String DESCRIPTION_PREFIX = "Description: ";
    private static final String[] PRODUCT_NAMES = {"Product1", "Product2", "Product3"};
    private static final String UPDATED_NAME = "UpdatedProductName";

    @Test
    @Override
    public void testFindAll() {
        for(String testName : PRODUCT_NAMES) {
            Product product = createEntity();
            product.setName(testName);
            product.setDescription(DESCRIPTION_PREFIX + testName);
            dao.create(product);
        }

        List<Product> products = dao.findAll();
        Assert.assertEquals(products.size(), PRODUCT_NAMES.length);
        for(Product product : products) {
            Assert.assertTrue(Arrays.asList(PRODUCT_NAMES).contains(product.getName()));
            Assert.assertTrue(product.getDescription().startsWith(DESCRIPTION_PREFIX));
        }

        for(Product product : products) {
            dao.delete(product.getId());
            Assert.assertNull(dao.findById(product.getId()));
        }
    }

    @Override
    protected void assertUpdatedEntity(Product expected, Product updated) {
        Assert.assertEquals(updated.getName(), expected.getName());
        Assert.assertEquals(updated.getDescription(), expected.getDescription());
        Assert.assertNotNull(updated.getUpdated());
    }

    @Autowired
    @Override
    protected void setDao(ProductDao dao) {
        super.dao = dao;
    }

    @Override
    protected Product createEntity() {
        String name = PRODUCT_NAMES[DEFAULT_INDEX];
        return TestEntityFactory.createProduct(name, DESCRIPTION_PREFIX + name);
    }

    @Override
    protected void updateEntity(Product entity) {
        entity.setName(UPDATED_NAME);
        entity.setDescription(DESCRIPTION_PREFIX + UPDATED_NAME);
        entity.setUpdated(new Date());
    }
}
