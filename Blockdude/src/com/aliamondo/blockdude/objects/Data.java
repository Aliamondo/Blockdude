package com.aliamondo.blockdude.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Data {
	private int totalStars;
	private int level;
	private int maxLevel;
	private int starsCollected = 0;
	private boolean firstRunEver = false;
	private boolean cheater = false;
	private boolean gameComplete = false;
	private Achievement a = new Achievement();
	
	private ArrayList<Achievement> achievementQueue = new ArrayList<Achievement>();

	public Data() {
		try {
			FileHandle file = Gdx.files.local( "data.txt" );
			String[] contents = file.readString().split("\\r?\\n");
			this.level = Integer.parseInt(contents[0]);
			this.totalStars = Integer.parseInt(contents[1]);
			this.maxLevel = Integer.parseInt(contents[2]);
			this.gameComplete = Boolean.parseBoolean(contents[3]);
		} catch (Exception e) {
			System.out.println(e.toString());
			this.level = 1;
			this.totalStars = 0;
			this.maxLevel = this.level;
			this.firstRunEver = true;
			this.gameComplete = false;
		}
	}
	
	public void addAchievementInQueue(Achievement ach) {
		for (Achievement temp : achievementQueue) {
			if (temp.text.equals(ach.text)) {
				return;
			}
		}
		achievementQueue.add(ach);
	}
	
	public Achievement getAchievementFromQueue() {
		if (achievementQueue.size() > 0) {
			Achievement ach = achievementQueue.get(0);
			achievementQueue.remove(0);
			return ach;
		}
		return null;
	}
	
	public void showNextAchievement() {
		if (achievementQueue.size() > 0 && a.doneShowing()) {
			a = getAchievementFromQueue();
		}
		if (a != null) {
			a.show();
		}
	}

//	public void finishShowingAchievement() {
//		a.t = 4;
//	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int i) {
		level = i;
		if (level >= maxLevel) maxLevel = level;
//		if (maxLevel >= 12) {
//			gameComplete = true;
//		}
	}
	public int getTotalStars() {
		return totalStars;
	}
	public int getStarsCollected() {
		return starsCollected;
	}
	public void setStarsCollected(int n) {
		starsCollected = n;
	}
	public void setTotalStars(int n) {
		totalStars = n;
		firstRunEver = false;
	}
	public int getMaxLevel() {
		return maxLevel;
	}
	public boolean isFirstRunEver() {
		return firstRunEver;
	}
	public void setCheater(boolean cheater) {
		this.cheater = cheater;
	}
	public boolean getCheater() {
		return cheater;
	}
	public boolean getGameComplete() {
		return gameComplete;
	}
	public void setGameComplete(boolean gameComplete) {
		this.gameComplete = gameComplete;
	}
	public float convertScreenWidth(float x) {
		return (x / 800) * Gdx.graphics.getWidth();
	}
	public float convertScreenHeight(float y) {
		return (y / 480) * Gdx.graphics.getHeight();
	}
}
