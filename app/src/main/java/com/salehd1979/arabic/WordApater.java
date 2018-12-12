package com.salehd1979.arabic;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class WordApater extends ArrayAdapter<Word>
{
	public WordApater(Context context, ArrayList<Word> words){
		super(context, 0, words);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		View listItemView = convertView;
		if (listItemView == null){
			listItemView = LayoutInflater.from(getContext()).inflate(
				R.layout.list_item, parent , false);
		}
		
		Word currentWord = getItem(position);
		
		TextView letterTextView = (TextView) listItemView.findViewById(R.id.txtViewItem);
		letterTextView.setText(currentWord.getWord());
		return listItemView;
		
	}
	
	
}
