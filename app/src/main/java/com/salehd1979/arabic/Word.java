package com.salehd1979.arabic;

public class Word
{
	private String mWord;
	private int mAudioResourseId;
	
	public Word(String w, int audioResourseId){
		mWord = w;
		mAudioResourseId = audioResourseId;
	}
	
	
	public String getWord(){
		return mWord;
	}
	
	public int getAudioResourseId(){
		return mAudioResourseId;
	}
	
	
}
