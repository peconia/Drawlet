package com.example.drawlet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;



public class MainActivity extends Activity implements OnClickListener {
	
	//custom drawing view
	private DrawingView drawView;
	//paint color
	private ImageButton currPaint, drawButton, eraseButton, saveButton, newButton;
	//different brushes
	private float tinyBrush, smallBrush, mediumBrush, largeBrush;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get drawing view
		drawView = (DrawingView)findViewById(R.id.drawing);
		
		//get the palette and first color button
		LinearLayout paintLayout = (LinearLayout)findViewById(R.id.paint_colors);
		currPaint = (ImageButton)paintLayout.getChildAt(0);
		currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
		
		// select brush
		tinyBrush = getResources().getInteger(R.integer.tiny_size);
		smallBrush = getResources().getInteger(R.integer.small_size);
		mediumBrush = getResources().getInteger(R.integer.medium_size);
		largeBrush = getResources().getInteger(R.integer.large_size);
		drawView.setBrushSize(smallBrush);
		
		//buttonses
		drawButton = (ImageButton)findViewById(R.id.draw_button);
		drawButton.setOnClickListener(this);
		
		eraseButton = (ImageButton)findViewById(R.id.erase_button);
		eraseButton.setOnClickListener(this);
		
		newButton = (ImageButton)findViewById(R.id.new_button);
		newButton.setOnClickListener(this);
		
		saveButton = (ImageButton)findViewById(R.id.save_button);
		saveButton.setOnClickListener(this);
		
		
		

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.draw_button) {
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Choose brush size:");
			brushDialog.setContentView(R.layout.brush_chooser);	
			
			// listen for clicks on size buttons
			
			ImageButton tinyButton = (ImageButton)brushDialog.findViewById(R.id.tiny_brush);
			tinyButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(tinyBrush);
					drawView.setLastBrushSize(tinyBrush);
					drawView.setErase(false);
					brushDialog.dismiss();					
				}
			});
			
			ImageButton smallButton = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(smallBrush);
					drawView.setLastBrushSize(smallBrush);
					drawView.setErase(false);
					brushDialog.dismiss();					
				}
			});
			
			ImageButton mediumButton = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(mediumBrush);
					drawView.setLastBrushSize(mediumBrush);
					drawView.setErase(false);
					brushDialog.dismiss();					
				}
			});
			
			ImageButton largeButton = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setBrushSize(largeBrush);
					drawView.setLastBrushSize(largeBrush);
					drawView.setErase(false);
					brushDialog.dismiss();					
				}
			});
			
			brushDialog.show();
		}
		else if (view.getId()==R.id.erase_button) {
			final Dialog brushDialog = new Dialog(this);
			brushDialog.setTitle("Choose eraser size:");
			brushDialog.setContentView(R.layout.brush_chooser);
			
			ImageButton tinyButton = (ImageButton)brushDialog.findViewById(R.id.tiny_brush);
			tinyButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(tinyBrush);
					brushDialog.dismiss();					
				}
			});
			
			ImageButton smallButton = (ImageButton)brushDialog.findViewById(R.id.small_brush);
			smallButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(smallBrush);
					brushDialog.dismiss();					
				}
			});
			
			ImageButton mediumButton = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
			mediumButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(mediumBrush);
					brushDialog.dismiss();					
				}
			});
			
			ImageButton largeButton = (ImageButton)brushDialog.findViewById(R.id.large_brush);
			largeButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					drawView.setErase(true);
					drawView.setBrushSize(largeBrush);
					brushDialog.dismiss();					
				}
			});
			brushDialog.show();
		}
		else if (view.getId()==R.id.new_button) {
			AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
			newDialog.setTitle("New Drawing");
			newDialog.setMessage("Are you sure you want to start a new drawing? \n\nYou will lose any unsaved work!");
			newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//				@Override
				public void onClick(DialogInterface dialog, int which) {
					drawView.startNew();
					dialog.dismiss();					
				}
			});
			newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			newDialog.show();
		}
		
		else if (view.getId()==R.id.save_button) {
			AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
			saveDialog.setTitle("Save Drawing");
			saveDialog.setMessage("Save drawing to device Gallery?");
			saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//				@Override
				public void onClick(DialogInterface dialog, int which) {
					drawView.setDrawingCacheEnabled(true);
					String imageSaved = MediaStore.Images.Media.insertImage(getContentResolver(), drawView.getDrawingCache(), 
							UUID.randomUUID().toString()+".png", "drawing");
					
					if (imageSaved != null) {
						Toast savedToast = Toast.makeText(getApplicationContext(), "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
						savedToast.show();
					}
					else { 
						Toast unsavedToast = Toast.makeText(getApplicationContext(), 
								"Ooops, failed to save your pic. It is probabaly not worth it anyways.", Toast.LENGTH_SHORT);
						unsavedToast.show();
					}
					drawView.destroyDrawingCache();
				}
			});
			saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			saveDialog.show();
			
		}
		
	}
	
	//user clicked paint
	public void paintClicked(View view){
		//use chosen color	
		drawView.setErase(false);
		drawView.setBrushSize(drawView.getLastBrushSize());
		if(view!=currPaint){
			ImageButton imgView = (ImageButton)view;
			String color = view.getTag().toString();
			drawView.setColor(color);
			//update ui
			imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
			currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
			currPaint=(ImageButton)view;
		}
	}

}


