package com.mycompany.a3.styledcomponents;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.plaf.Border;

public class customButton extends Button {
	public customButton(Button tmp) {
		tmp.getAllStyles().setFgColor(ColorUtil.WHITE);
		tmp.getAllStyles().setMargin(4, 4, 10, 10);
		tmp.getAllStyles().setPadding(TOP, 4);
		tmp.getAllStyles().setPadding(BOTTOM, 4);
		tmp.getAllStyles().setPadding(LEFT, 3);
		tmp.getAllStyles().setPadding(RIGHT, 3);
		tmp.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.GRAY));
		tmp.getAllStyles().setBgColor(ColorUtil.BLUE);
		tmp.getAllStyles().setBgTransparency(255);
	}
}
