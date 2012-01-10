// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package fi.opendemocracy.voting.domain;

import fi.opendemocracy.voting.domain.CategoryDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CategoryIntegrationTest_Roo_IntegrationTest {
    
    declare @type: CategoryIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: CategoryIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: CategoryIntegrationTest: @Transactional;
    
    @Autowired
    private CategoryDataOnDemand CategoryIntegrationTest.dod;
    
    @Test
    public void CategoryIntegrationTest.testCountCategorys() {
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", dod.getRandomCategory());
        long count = fi.opendemocracy.voting.domain.Category.countCategorys();
        org.junit.Assert.assertTrue("Counter for 'Category' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void CategoryIntegrationTest.testFindCategory() {
        fi.opendemocracy.voting.domain.Category obj = dod.getRandomCategory();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to provide an identifier", id);
        obj = fi.opendemocracy.voting.domain.Category.findCategory(id);
        org.junit.Assert.assertNotNull("Find method for 'Category' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Category' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void CategoryIntegrationTest.testFindAllCategorys() {
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", dod.getRandomCategory());
        long count = fi.opendemocracy.voting.domain.Category.countCategorys();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Category', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<fi.opendemocracy.voting.domain.Category> result = fi.opendemocracy.voting.domain.Category.findAllCategorys();
        org.junit.Assert.assertNotNull("Find all method for 'Category' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Category' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void CategoryIntegrationTest.testFindCategoryEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", dod.getRandomCategory());
        long count = fi.opendemocracy.voting.domain.Category.countCategorys();
        if (count > 20) count = 20;
        java.util.List<fi.opendemocracy.voting.domain.Category> result = fi.opendemocracy.voting.domain.Category.findCategoryEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Category' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Category' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void CategoryIntegrationTest.testFlush() {
        fi.opendemocracy.voting.domain.Category obj = dod.getRandomCategory();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to provide an identifier", id);
        obj = fi.opendemocracy.voting.domain.Category.findCategory(id);
        org.junit.Assert.assertNotNull("Find method for 'Category' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCategory(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Category' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void CategoryIntegrationTest.testMerge() {
        fi.opendemocracy.voting.domain.Category obj = dod.getRandomCategory();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to provide an identifier", id);
        obj = fi.opendemocracy.voting.domain.Category.findCategory(id);
        boolean modified =  dod.modifyCategory(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        fi.opendemocracy.voting.domain.Category merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Category' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void CategoryIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", dod.getRandomCategory());
        fi.opendemocracy.voting.domain.Category obj = dod.getNewTransientCategory(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Category' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Category' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void CategoryIntegrationTest.testRemove() {
        fi.opendemocracy.voting.domain.Category obj = dod.getRandomCategory();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Category' failed to provide an identifier", id);
        obj = fi.opendemocracy.voting.domain.Category.findCategory(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'Category' with identifier '" + id + "'", fi.opendemocracy.voting.domain.Category.findCategory(id));
    }
    
}
