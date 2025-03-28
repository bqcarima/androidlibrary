package org.opendatakit.database.data;

import static org.junit.Assert.*;

import android.os.Parcel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@SuppressWarnings("ALL")
@RunWith(AndroidJUnit4.class)
public class KeyValueStoreEntryTest {

    private KeyValueStoreEntry ENTRY_1;
    private KeyValueStoreEntry ENTRY_2;

    // Constants class containing content to be used in tests
    private static class Constants {
        private static final String TABLE_ID = "testTable";

        // first test entry contents
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

    // Test that the KeyValueStoreEntry is initialized correctly and is empty
    @Test
    public void testKeyValueStoreEntryInitialization() {
        ENTRY_1 = new KeyValueStoreEntry();
        assertNull(ENTRY_1.tableId);
        assertNull(ENTRY_1.partition);
        assertNull(ENTRY_1.aspect);
        assertNull(ENTRY_1.key);
        assertNull(ENTRY_1.type);
        assertNull(ENTRY_1.value);
    }

    @Before
    public void setUp() {
        // First test entry
        ENTRY_1 = new KeyValueStoreEntry();
        ENTRY_1.tableId = Constants.TABLE_ID;
        ENTRY_1.partition = Constants.PARTITION1;
        ENTRY_1.aspect = Constants.ASPECT1;
        ENTRY_1.key = Constants.KEY1;
        ENTRY_1.type = Constants.TYPE1;
        ENTRY_1.value = Constants.VALUE1;

        // Second test entry
        ENTRY_2 = new KeyValueStoreEntry();
        ENTRY_2.tableId = Constants.TABLE_ID;
        ENTRY_2.partition = Constants.PARTITION2;
        ENTRY_2.aspect = Constants.ASPECT2;
        ENTRY_2.key = Constants.KEY2;
        ENTRY_2.type = Constants.TYPE2;
        ENTRY_2.value = Constants.VALUE2;
    }

    @Test
    public void testToString() {
        String str = ENTRY_1.toString();
        assertTrue(str.contains(Constants.TABLE_ID));
        assertTrue(str.contains(Constants.PARTITION1));
        assertTrue(str.contains(Constants.ASPECT1));
        assertTrue(str.contains(Constants.KEY1));
        assertTrue(str.contains(Constants.TYPE1));
        assertTrue(str.contains(Constants.VALUE1));
    }

    @Test
    public void testHashCodeConsistency() {

        Parcel parcel = Parcel.obtain();
        ENTRY_1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Create a new KeyStoreValueEntry object from the parcel
        KeyValueStoreEntry createdFromParcel = new KeyValueStoreEntry(parcel);

        // Checking hash code consistency.
        // If the data contained each KeyValueStoreEntry object
        // is the same, the hash codes should be the same
        assertEquals(ENTRY_1.hashCode(), createdFromParcel.hashCode());
    }

    @Test
    public void testHashCodeDifference() {
        // Checking hash code consistency.
        // If the data contained within each KeyValueStoreEntry object is
        // different, then the hash codes should be the different as well.
        assertNotEquals(ENTRY_1.hashCode(), ENTRY_2.hashCode());
    }

    @Test
    public void testEqualsNull() {
        // Test for equality against null objects
        KeyValueStoreEntry key = null;
        assertFalse(ENTRY_1.equals(key));
    }

    @Test
    public void testEqualsSameObject() {
        // Test same instance of an object for equality
        assertTrue(ENTRY_1.equals(ENTRY_1));
    }

    @Test
    public void testEqualsDifferentObject() {
        // Test different objects of the same type for equality
        assertFalse(ENTRY_1.equals(ENTRY_2));
    }

    @Test
    public void testEqualsTypeMismatch() {
        String str = "string";
        // Test for equality against an object of a different type
        assertFalse(ENTRY_1.equals(str));
    }

    @Test
    public void testCompareToPartition() {
        // Test for partition comparison

        // ENTRY_1 should be placed before ENTRY_2
        assertTrue(ENTRY_1.compareTo(ENTRY_2) < 0);

        // ENTRY_2 should be placed after ENTRY_1
        assertTrue(ENTRY_2.compareTo(ENTRY_1) > 0);
    }

    @Test
    public void testCompareToAspect() {
        // Test for aspect comparison

        // Modifying ENTRY_2 to have the same partition
        // as ENTRY_1 and a different aspect
        ENTRY_2.partition = Constants.PARTITION1;

        // ENTRY_1 should be placed before ENTRY_2
        assertTrue(ENTRY_1.compareTo(ENTRY_2) < 0);

        // ENTRY_2 should be placed after ENTRY_1
        assertTrue(ENTRY_2.compareTo(ENTRY_1) > 0);
    }

    @Test
    public void testCompareToKey() {
        // Test for key comparison

        // Modifying ENTRY_2 to have the same partition
        // and aspect as ENTRY_1 and only a different key
        ENTRY_2.partition = Constants.PARTITION1;
        ENTRY_2.aspect = Constants.ASPECT1;

        // ENTRY_1 should be placed before ENTRY_2
        assertTrue(ENTRY_1.compareTo(ENTRY_2) < 0);

        // ENTRY_2 should be placed after ENTRY_1
        assertTrue(ENTRY_2.compareTo(ENTRY_1) > 0);
    }

    @Test
    public void testCompareToEquality() {
        // Test for equality comparison

        // Modifying ENTRY_2 to have the same partition
        // and aspect and key as ENTRY_1
        ENTRY_2.partition = Constants.PARTITION1;
        ENTRY_2.aspect = Constants.ASPECT1;
        ENTRY_2.key = Constants.KEY1;

        // Order should not matter for in an equality test
        // ENTRY_1 should be equal to ENTRY_2
        assertEquals(0, ENTRY_1.compareTo(ENTRY_2));

        // ENTRY_2 should be equal to ENTRY_1
        assertEquals(0, ENTRY_2.compareTo(ENTRY_1));
    }

    @Test
    public void testDescribeContents() {
        assertEquals(0, ENTRY_1.describeContents());
    }

    // Test for data integrity during reading and writing to a parcel
    // (serialization and deserialization)
    @Test
    public void testWriteToParcel() {
        // Write the KeyStoreValueEntry object to a parcel
        Parcel parcel = Parcel.obtain();
        ENTRY_1.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);

        // Read entry data and verify that the data is the same
        assertEquals(Constants.TABLE_ID, parcel.readString());
        assertEquals(Constants.PARTITION1, parcel.readString());
        assertEquals(Constants.ASPECT1, parcel.readString());
        assertEquals(Constants.KEY1, parcel.readString());
        assertEquals(Constants.TYPE1, parcel.readString());
        assertEquals(Constants.VALUE1, parcel.readString());
    }

}

