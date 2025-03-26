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

    //
    private TableMetaDataEntries testMetaDataEntries;
    private final String TABLE_ID = "testTable";
    private final String REV_ID = "revId";
    private final KeyValueStoreEntry ENTRY_1 = new KeyValueStoreEntry();
    private final KeyValueStoreEntry ENTRY_2 = new KeyValueStoreEntry();

    @Before
    public void setUp() {
        testMetaDataEntries = new TableMetaDataEntries(TABLE_ID, REV_ID);

        // first test entry
        ENTRY_1.tableId = TABLE_ID;
        ENTRY_1.partition = "partition1";
        ENTRY_1.aspect = "aspect1";
        ENTRY_1.key = "key1";
        ENTRY_1.type = "number";
        ENTRY_1.value = "value1";

        // second test entry
        ENTRY_2.tableId = TABLE_ID;
        ENTRY_2.partition = "partition2";
        ENTRY_2.aspect = "aspect2";
        ENTRY_2.key = "key2";
        ENTRY_2.type = "number";
        ENTRY_2.value = "value2";

    }

    @Test
    public void testDatabaseInitialization() { // test that an empty database is initialized
        assertEquals(TABLE_ID, testMetaDataEntries.getTableId());
        assertEquals(REV_ID, testMetaDataEntries.getRevId());
        assertTrue(testMetaDataEntries.getEntries().isEmpty());
    }

    @Test
    public void testAddEntry() {
        testMetaDataEntries.addEntry(ENTRY_1);
        testMetaDataEntries.addEntry(ENTRY_2);

        ArrayList<KeyValueStoreEntry> entries = testMetaDataEntries.getEntries();
        assertEquals(2, entries.size());
        assertEquals(ENTRY_1, entries.get(0));
        assertEquals(ENTRY_2, entries.get(1));
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
