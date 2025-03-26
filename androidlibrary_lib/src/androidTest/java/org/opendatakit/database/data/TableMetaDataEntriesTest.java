package org.opendatakit.database.data;

import android.os.Parcel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TableMetaDataEntriesTest {

    private TableMetaDataEntries testMetaDataEntries;

    private final KeyValueStoreEntry ENTRY_1 = new KeyValueStoreEntry();
    private final KeyValueStoreEntry ENTRY_2 = new KeyValueStoreEntry();

    // Contants class containing content to be used in tests
    private static class Constants {

        private static final String TABLE_ID = "testTable";
        private static final String REV_ID = "revId";

        // first test entry constents
        private static final String PARTITION1 = "partition1";
        private static final String ASPECT1 = "aspect1";
        private static final String KEY1 = "key1";
        private static final String TYPE1 = "number";
        private static final String VALUE1 = "value1";

        // second test entry contents
        private static final String PARTITION2 = "partition2";
        private static final String ASPECT2 = "aspect2";
        private static final String KEY2 = "key2";
        private static final String TYPE2 = "number";
        private static final String VALUE2 = "value2";
    }

    @Before
    public void setUp() {
        testMetaDataEntries = new TableMetaDataEntries(Constants.TABLE_ID, Constants.REV_ID);

        // first test entry
        ENTRY_1.tableId = Constants.TABLE_ID;
        ENTRY_1.partition = Constants.PARTITION1;
        ENTRY_1.aspect = Constants.ASPECT1;
        ENTRY_1.key = Constants.KEY1;
        ENTRY_1.type = Constants.TYPE1;
        ENTRY_1.value = Constants.VALUE1;

        // second test entry
        ENTRY_2.tableId = Constants.TABLE_ID;
        ENTRY_2.partition = Constants.PARTITION2;
        ENTRY_2.aspect = Constants.ASPECT2;
        ENTRY_2.key = Constants.KEY2;
        ENTRY_2.type = Constants.TYPE2;
        ENTRY_2.value = Constants.VALUE2;

    }

    @Test
    public void testDatabaseInitialization() { // test that an empty database is initialized
        assertEquals(Constants.TABLE_ID, testMetaDataEntries.getTableId());
        assertEquals(Constants.REV_ID, testMetaDataEntries.getRevId());
        assertTrue(testMetaDataEntries.getEntries().isEmpty());
    }

    @Test
    public void testAddEntry() {
        testMetaDataEntries.addEntry(ENTRY_1);
        testMetaDataEntries.addEntry(ENTRY_2);

        // Verify that the entries were added correctly
        ArrayList<KeyValueStoreEntry> entries = testMetaDataEntries.getEntries();
        assertEquals(2, entries.size());
        assertEquals(ENTRY_1, entries.get(0));
        assertEquals(ENTRY_2, entries.get(1));

        // Verify that the contents of the entries are correct
        // ENTRY_1
        assertEquals(Constants.TABLE_ID, entries.get(0).tableId);
        assertEquals(Constants.PARTITION1, entries.get(0).partition);
        assertEquals(Constants.ASPECT1, entries.get(0).aspect);
        assertEquals(Constants.KEY1, entries.get(0).key);
        assertEquals(Constants.TYPE1, entries.get(0).type);
        assertEquals(Constants.VALUE1, entries.get(0).value);

        // ENTRY_2
        assertEquals(Constants.TABLE_ID, entries.get(1).tableId);
        assertEquals(Constants.PARTITION2, entries.get(1).partition);
        assertEquals(Constants.ASPECT2, entries.get(1).aspect);
        assertEquals(Constants.KEY2, entries.get(1).key);
        assertEquals(Constants.TYPE2, entries.get(1).type);
        assertEquals(Constants.VALUE2, entries.get(1).value);
    }

    @Test
    public void testDescribeContents() {
        assertEquals(0, testMetaDataEntries.describeContents());
    }

    @Test
    public void testWriteToParcel() {
        testMetaDataEntries.addEntry(ENTRY_1);

        // Write the testMetaDataEntries object to a parcel
        Parcel parcel = Parcel.obtain();
        testMetaDataEntries.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Create new MetaDataEntries object from the parcel
        TableMetaDataEntries createdFromParcel = TableMetaDataEntries.CREATOR.createFromParcel(parcel);

        // Verify that the data is the same
        assertEquals(testMetaDataEntries.getTableId(), createdFromParcel.getTableId());
        assertEquals(testMetaDataEntries.getRevId(), createdFromParcel.getRevId());
        assertEquals(testMetaDataEntries.getEntries().size(), createdFromParcel.getEntries().size());

        assertEquals(testMetaDataEntries.getEntries().get(0).tableId, createdFromParcel.getEntries().get(0).tableId);
        assertEquals(testMetaDataEntries.getEntries().get(0).partition, createdFromParcel.getEntries().get(0).partition);
        assertEquals(testMetaDataEntries.getEntries().get(0).aspect, createdFromParcel.getEntries().get(0).aspect);
        assertEquals(testMetaDataEntries.getEntries().get(0).key, createdFromParcel.getEntries().get(0).key);
        assertEquals(testMetaDataEntries.getEntries().get(0).type, createdFromParcel.getEntries().get(0).type);
        assertEquals(testMetaDataEntries.getEntries().get(0).value, createdFromParcel.getEntries().get(0).value);
    }

    @Test
    public void testEntryOrderPreservation() {
        testMetaDataEntries.addEntry(ENTRY_2);
        testMetaDataEntries.addEntry(ENTRY_1);

        ArrayList<KeyValueStoreEntry> entries = testMetaDataEntries.getEntries();
        assertEquals(ENTRY_2, entries.get(0));
        assertEquals(ENTRY_1, entries.get(1));
    }
}

