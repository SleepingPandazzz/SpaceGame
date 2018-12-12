package com.mycompany.a3.styledcomponents;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Label;

public class customLabel extends Label {
	public customLabel(Label tmp) {
		tmp.getAllStyles().setFgColor(ColorUtil.BLACK);
	}
}
