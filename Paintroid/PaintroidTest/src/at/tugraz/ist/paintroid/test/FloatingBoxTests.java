/*    Catroid: An on-device graphical programming language for Android devices
 *    Copyright (C) 2010  Catroid development team
 *    (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.tugraz.ist.paintroid.test;

import java.util.Locale;

import android.content.res.Configuration;
import android.graphics.Point;
import android.test.ActivityInstrumentationTestCase2;
import at.tugraz.ist.paintroid.MainActivity;
import at.tugraz.ist.paintroid.graphic.DrawingSurface.Mode;

import com.jayway.android.robotium.solo.Solo;

public class FloatingBoxTests extends ActivityInstrumentationTestCase2<MainActivity> {

	private Solo solo;
	private MainActivity mainActivity;
	private int screenWidth;
	private int screenHeight;

	// Buttonindexes
	final int COLORPICKER = 0;
	final int STROKE = 0;
	final int HAND = 1;
	final int MAGNIFIY = 2;
	final int BRUSH = 3;
	final int EYEDROPPER = 4;
	final int WAND = 5;
	final int UNDO = 6;
	final int REDO = 7;
	final int FILE = 8;
	
	final int STROKERECT = 0;
	final int STROKECIRLCE = 1;
	final int STROKE1 = 2;
	final int STROKE2 = 3;
	final int STROKE3 = 4;
	final int STROKE4 = 5;

	public FloatingBoxTests() {
		super("at.tugraz.ist.paintroid", MainActivity.class);
	}

	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		String languageToLoad_before  = "en";
		Locale locale_before = new Locale(languageToLoad_before);
		Locale.setDefault(locale_before);
		
		Configuration config_before = new Configuration();
		config_before.locale = locale_before;
		
		mainActivity = (MainActivity) solo.getCurrentActivity();
		mainActivity.getBaseContext().getResources().updateConfiguration(config_before, mainActivity.getBaseContext().getResources().getDisplayMetrics());
		
		screenWidth = solo.getCurrentActivity().getWindowManager()
		  .getDefaultDisplay().getWidth();
		screenHeight = solo.getCurrentActivity().getWindowManager()
		  .getDefaultDisplay().getHeight();
	}

	/**
	 * Check if the floating box mode is activated after pressing the menu button
	 * and deactivated after pressing it again.
	 * 
	 */
	public void testFloatingBoxModes() throws Exception {
		solo.clickOnImageButton(FILE);
		solo.clickOnButton("New Drawing");
		
		solo.clickOnMenuItem("Stamp");
		Thread.sleep(200);
		assertEquals(Mode.FLOATINGBOX, mainActivity.getMode());
		
		solo.clickOnMenuItem("Stamp");
		Thread.sleep(200);
		assertEquals(Mode.DRAW, mainActivity.getMode());
	}
	
	/**
	 * Check if the floating box is working correctly
	 * 
	 */
	public void testFloatingBox() throws Exception {
		solo.clickOnImageButton(FILE);
		solo.clickOnButton("New Drawing");
		
		solo.clickOnMenuItem("Stamp");
		Thread.sleep(200);
		assertEquals(Mode.FLOATINGBOX, mainActivity.getMode());
		
		solo.drag(screenWidth/2, screenWidth/2+200, screenHeight/2, screenHeight/2+50, 10);
		
		Point coordinates = new Point(0,0);
		coordinates = mainActivity.getFloatingBoxCoordinates();
		
		solo.clickOnMenuItem("Stamp");
		Thread.sleep(200);
		assertEquals(Mode.DRAW, mainActivity.getMode());
		
		assertTrue(coordinates.equals(screenWidth/2+200, screenHeight/2+50));
	}
	
	/**
	 * Check if the floating box stamp function is working correctly
	 * 
	 */
	public void testFloatingBoxStamp() throws Exception {
		solo.clickOnImageButton(FILE);
		solo.clickOnButton("New Drawing");
		
		solo.clickOnImageButton(BRUSH);

		solo.clickOnScreen(screenWidth/2-100, screenHeight/2);
		
		solo.clickOnMenuItem("Stamp");
		Thread.sleep(200);
		assertEquals(Mode.FLOATINGBOX, mainActivity.getMode());
		
		solo.drag(screenWidth/2, screenWidth/2-100, screenHeight/2, screenHeight/2, 10);
		
		Thread.sleep(500);
		
		solo.clickOnScreen(screenWidth/2-100, screenHeight/2);
		
		Thread.sleep(500);
		
		solo.drag(screenWidth/2-100, screenWidth/2+100, screenHeight/2, screenHeight/2+50, 10);
		
		Point coordinates = new Point(0,0);
		coordinates = mainActivity.getFloatingBoxCoordinates();
		
		solo.clickOnScreen(screenWidth/2+100, screenHeight/2+50);
		
		solo.clickOnMenuItem("Stamp");
		Thread.sleep(200);
		assertEquals(Mode.DRAW, mainActivity.getMode());
		
		assertTrue(coordinates.equals(screenWidth/2+100, screenHeight/2+50));
	}
	
	/**
	 * Check if the floating box triggers a drag if moved to the edge of the screen
	 * 
	 */
	public void testFloatingBoxDrag() throws Exception {
		//TODO
	}

	@Override
	public void tearDown() throws Exception {
		try {
			solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		getActivity().finish();
		super.tearDown();
	}

}