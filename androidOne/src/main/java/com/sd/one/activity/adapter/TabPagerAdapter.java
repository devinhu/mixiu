/*
    ShengDao Android Client, HomePagerAdapter
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.sd.one.activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * [首页分类适配器]
 * 
 * @author huxinwu
 * @version 1.0
 * @date 2014-10-27
 * 
 **/
public class TabPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;
	private String[] titles;

	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public CharSequence getPageTitle(int position) {
		if (titles != null) {
			return titles[position];
		}
		return null;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public List<Fragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

}
