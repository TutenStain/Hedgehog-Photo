package se.cth.hedgehogphoto.view;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.plaf.LayerUI;

/**
 * @modified Barnabas Sapan
 *  
 *  
 *  
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

@SuppressWarnings("serial")
class LoadingLayer extends LayerUI<JPanel> implements ActionListener {
	private JLayer<JPanel> decoratedPanel; 

	private boolean mIsRunning;
	private boolean mIsFadingOut;
	private Timer mTimer;

	private int mAngle;
	private int mFadeCount;
	private int mFadeLimit = 20;

	/**
	 * 
	 * @param panel the panel to decorate with a loading spinner.
	 */
	public LoadingLayer(JPanel panel){
		decoratedPanel = new JLayer<JPanel>(panel, this);
	}

	/**
	 * 
	 * @return returns the decorated animated layer ready to be added to a container.
	 */
	public JLayer<JPanel> getDecoratedPanel(){
		return decoratedPanel;
	}

	/**
	 * Starts the animations, runs until stop is called.
	 */
	public void start() {
		if (mIsRunning) {
			return;
		}

		// Run a thread for animation.
		mIsRunning = true;
		mIsFadingOut = false;
		mFadeCount = 0;
		int fps = 30;
		int tick = 1000 / fps;
		mTimer = new Timer(tick, this);
		mTimer.start();
	}

	/**
	 * Stops the current animation while keeping the panel intact.
	 */
	public void stop() {
		mIsFadingOut = true;
	}
	
	/**
	 * Stops the current animation and removes ourself.
	 */
	public void stopAndRemove(){
		this.stop();
		//Remove ourself
		decoratedPanel.removeAll();
	}

	@Override
	public void paint (Graphics g, JComponent c) {
		int w = c.getWidth();
		int h = c.getHeight();

		// Paint the view.
		super.paint (g, c);

		if (!mIsRunning) {
			return;
		}

		Graphics2D g2 = (Graphics2D)g.create();

		float fade = (float)mFadeCount / (float)mFadeLimit;
		// Gray it out.
		Composite urComposite = g2.getComposite();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .25f * fade));
		g2.fillRect(0, 0, w, h);
		g2.setComposite(urComposite);

		// Paint the wait indicator.
		int s = Math.min(w, h) / 5;
		int cx = w / 2;
		int cy = h / 2;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(s / 4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.setPaint(Color.WHITE);
		g2.rotate(Math.PI * mAngle / 180, cx, cy);
		for (int i = 0; i < 12; i++) {
			float scale = (12.0f - (float)i) / 12.0f;
			g2.drawLine(cx + s, cy, cx + s * 2, cy);
			g2.rotate(-Math.PI / 6, cx, cy);
			g2.setComposite(AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, scale * fade));
		}

		g2.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (mIsRunning) {
			firePropertyChange("tick", 0, 1);
			mAngle += 3;
			if (mAngle >= 360) {
				mAngle = 0;
			}
			if (mIsFadingOut) {
				if (--mFadeCount == 0) {
					mIsRunning = false;
					mTimer.stop();
				}
			}
			else if (mFadeCount < mFadeLimit) {
				mFadeCount++;
			}
		}
	}

	@Override
	public void applyPropertyChange(PropertyChangeEvent pce, JLayer l) {
		if ("tick".equals(pce.getPropertyName())) {
			l.repaint();
		}
	}
}