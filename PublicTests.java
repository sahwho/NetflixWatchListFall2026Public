import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import org.junit.Test;

/* @Author Vikash Sahu
 * ADSB Fall 2026
 * Project: Netflix WatchList
 * Public Test Cases
 * Last Updated: 10/15/2026
 */

// Ensure the class name matches the test file name; update both if either changes.
public class PublicTests {
    //reusable releaseYear
	LocalDateTime released = LocalDateTime.of(2022, 1, 1, 1, 1);
	
	//reusable expiring
	LocalDateTime expiring = LocalDateTime.of(2032, 10, 31, 11, 59);
	
	//reusable Media objects (series)
	Media m1 = new Media("Fakes", Media.Type.SERIES, Media.Genre.ACTION, released, 1, expiring, true);
	Media m2 = new Media("Outer Banks", Media.Type.SERIES, Media.Genre.ACTION, released, 3, expiring, true);
	Media m3 = new Media("Sense8", Media.Type.SERIES, Media.Genre.SCIENCE_FICTION, released, 5, expiring, false);
	Media m7 = new Media("Grey's Anatomy", Media.Type.SERIES, Media.Genre.DRAMA, released, 19, expiring, false);
	Media m8 = new Media("Suits", Media.Type.SERIES, Media.Genre.DRAMA, released, 5, expiring, true);
	Media m9 = new Media("Arrested Development", Media.Type.SERIES, Media.Genre.COMEDY, released, 4, expiring, true);
	
	//reusable Media objects (movie)
	Media m4 = new Media("To All the Boys", Media.Type.MOVIE, Media.Genre.DRAMA, released, -1, expiring, true);
	Media m5 = new Media("the half of it", Media.Type.MOVIE, Media.Genre.ROMANCE, released, -1, expiring, false);
	Media m6 = new Media("Murder Mystery", Media.Type.MOVIE, Media.Genre.DRAMA, released, -1, expiring, false);

    // utility method for getting the index of a Media object
	// in the array of Media objects
	public static int indexInQueue(Media m, Media[] array) {
		int index = -1;
		
		for (int i=0; i<array.length; i++) {
			if (array[i]==m)
				return i;
		}
		
		return index;
	}

    @Test
    public void testDefaultConstructorSizeCapacityQueue() {
		WatchList w = new WatchList();
		assertEquals(10, w.getCapacity());
		assertEquals(0, w.getSize());
		Media[] q = new Media[10];
		assertTrue(Arrays.equals(q, w.getQueue()));
	}

    @Test
	public void testConstructorWithParamSizeCapacityQueue() {
		WatchList w = new WatchList(7);
		assertEquals(7, w.getCapacity());
		assertEquals(0, w.getSize());
		Media[] q = new Media[7];
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testDefaultConstructorWithAppend() {
		WatchList w = new WatchList();
		assertTrue(w.append(m1));
		
		Media[] q = new Media[10];
		q[0] = m1;
		
		assertEquals(1, w.getSize());
		assertEquals(10, w.getCapacity());
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testDefaultConstructorWithAddToFront() {
		WatchList w = new WatchList();
		w.addToFront(m1);
		
		Media[] q = new Media[10];
		q[0] = m1;
		
		assertEquals(1, w.getSize());
		assertEquals(10, w.getCapacity());
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testClearWatchList() {
		WatchList w = new WatchList();
		assertTrue(w.append(m1));
		assertTrue(w.append(m2));
		
		assertEquals(2, w.getSize());
		assertEquals(10, w.getCapacity());
		
		w.clearWatchList();
		
		assertEquals(0, w.getSize());
		assertEquals(10, w.getCapacity());
		Media[] q = new Media[10];
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testTrimToSize() {
		WatchList w = new WatchList();
		assertTrue(w.append(m1));
		assertTrue(w.append(m2));
		
		assertEquals(10, w.getCapacity());
		assertEquals(2, w.getSize());
		
		Media[] q = new Media[2];
		q[0] = m1;
		q[1] = m2;
		
		w.trimToSize();
		assertEquals(2, w.getSize());
		assertEquals(2, w.getCapacity());
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testMarkMediaAsWatched() {
		WatchList w = new WatchList();
		assertTrue(w.append(m1));
		assertTrue(w.append(m2));

		assertTrue(w.markMediaAsWatched(m2));
		assertFalse(w.markMediaAsWatched(m3));
	}
	
	@Test
	public void testToString() {
		WatchList w = new WatchList(4);
		assertTrue(w.append(m1));
		assertTrue(w.append(m2));
		
		assertEquals("[" + m1.toString() + ", " + m2.toString() + ", null, null]", w.toString());
	}
	
	@Test
	public void testRemoveObjectsDefaultCapacity() {
		WatchList w = new WatchList();
		assertTrue(w.append(m2));
		assertTrue(w.append(m1));
		
		assertTrue(w.remove(m2));
		assertFalse(w.remove(m3));
		
		assertEquals(10, w.getCapacity());
		assertEquals(1, w.getSize());
		
		Media[] q = new Media[10];
		q[0] = m1;
		assertTrue(Arrays.equals(q, w.getQueue()));
	}

	@Test
	public void testRemoveAllGenreAndType() {
		WatchList w = new WatchList(2);
		assertEquals(0, w.getSize());
		assertEquals(2, w.getCapacity());
		
		assertTrue(w.append(m2));
		assertTrue(w.append(m1));
		assertEquals(4, w.getCapacity());
		assertEquals(2, w.getSize());
		w.addToFront(m3);
		
		assertEquals(2, w.removeAll(Media.Type.SERIES, Media.Genre.ACTION));
		
		assertEquals(4, w.getCapacity());
		assertEquals(1, w.getSize());
		
		Media[] q = new Media[4];
		q[0] = m3;
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testRemoveObjectsExplicitCapacity() {
		WatchList w = new WatchList(2);
		assertEquals(0, w.getSize());
		assertEquals(2, w.getCapacity());
		
		assertTrue(w.append(m2));
		assertTrue(w.append(m1));
		assertEquals(4, w.getCapacity());
		assertEquals(2, w.getSize());
		w.addToFront(m3);
		
		assertTrue(w.remove(m2));
		assertFalse(w.remove(m4));
		
		assertEquals(4, w.getCapacity());
		assertEquals(2, w.getSize());
		
		Media[] q = new Media[4];
		q[0] = m3;
		q[1] = m1;
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testRemoveWatched() {
		WatchList w = new WatchList(4);
		assertEquals(0, w.getSize());
		assertEquals(4, w.getCapacity());
		
		assertTrue(w.append(m5));
		assertTrue(w.append(m6));
		assertEquals(4, w.getCapacity());
		assertEquals(2, w.getSize());
		w.addToFront(m4);
		w.addToFront(m7);
		
		assertEquals(8, w.getCapacity());
		assertEquals(4, w.getSize());
		
		assertTrue(w.markMediaAsWatched(m5));
		assertTrue(w.markMediaAsWatched(m6));
		
		assertEquals(3, w.removeWatched());
		assertEquals(8, w.getCapacity());
		assertEquals(1, w.getSize());
		
		Media[] q = new Media[8];
		q[0] = m7;
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testAddToFrontWithDupesNoResize() {
		WatchList w = new WatchList(3);
		assertEquals(0, w.getSize());
		assertEquals(3, w.getCapacity());
		
		w.addToFront(m5);
		assertTrue(Arrays.equals(new Media[] {m5, null, null}, w.getQueue()));
		assertTrue(w.append(m6));
		assertTrue(Arrays.equals(new Media[] {m5, m6, null}, w.getQueue()));
		assertEquals(3, w.getCapacity());
		assertEquals(2, w.getSize());
		
		assertEquals(3, w.getCapacity());
		assertEquals(2, w.getSize());
		
		w.addToFront(m5);//dupe, no resize, just shuffle
		assertTrue(Arrays.equals(new Media[] {m5, m6, null}, w.getQueue()));
		w.addToFront(m6);//dupe, no resize, just shuffle
		
		assertEquals(3, w.getCapacity());
		assertEquals(2, w.getSize());
		
		Media[] q = new Media[3];
		q[0] = m6;
		q[1] = m5;
		assertTrue(Arrays.equals(q, w.getQueue()));
	}
	
	@Test
	public void testAddAtIndexSimple() {
		WatchList w = new WatchList(3); 
		assertTrue(w.addAtIndex(m1, 0));
		assertTrue(w.addAtIndex(m2, 1));
		 
		assertTrue(Arrays.equals(new Media[] {m1, m2, null}, w.getQueue()));
		assertEquals(2, w.getSize()); 
		assertEquals(3, w.getCapacity());
	}
	
	@Test
	public void testAddAtIndexThrowsIndexOutOfBoundsGreaterThanSize() {
		WatchList w = new WatchList(2); 
		assertTrue(w.addAtIndex(m1, 0));

		 assertThrows(IndexOutOfBoundsException.class,
            ()->{
				w.addAtIndex(m2, 2);
			});
		 
		assertTrue(Arrays.equals(new Media[] {m1, null}, w.getQueue()));
		assertEquals(1, w.getSize()); 
		assertEquals(2, w.getCapacity());
	}
	
	@Test
	public void testAddAtIndexThrowsIndexOutOfBoundsNegativeIndex() {
		WatchList w = new WatchList(2); 

		assertThrows(IndexOutOfBoundsException.class,
            ()->{
				w.addAtIndex(m2, -1);
			});
		 
		assertTrue(Arrays.equals(new Media[] {null, null}, w.getQueue()));
		assertEquals(0, w.getSize()); 
		assertEquals(2, w.getCapacity());
	}
	
	@Test
	public void testToArray() {
		WatchList w = new WatchList();
		assertTrue(w.append(m1));
		assertTrue(w.append(m2));
		assertTrue(w.append(m3));
		
		assertEquals(10, w.getCapacity());
		assertEquals(3, w.getSize());
		
		Media[] q = new Media[3];
		q[0] = m1;
		q[1] = m2;
		q[2] = m3;
		
		assertTrue(Arrays.equals(q, w.toArray()));
	}
}
