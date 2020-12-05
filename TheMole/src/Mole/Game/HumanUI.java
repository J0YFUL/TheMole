package Mole.Game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import MoleServer.MoleClientMainHandler;
import io.netty.channel.ChannelHandlerContext;

public class HumanUI extends JPanel {
	/*public static void main(String[] args) throws IOException, InterruptedException {
		new H();
	}*/

	private BufferedImage backImage;
	private BufferedImage humanHud;
	private BufferedImage humanInv;
	private BufferedImage intHuman;
	private Human human;
	
	private vegetableThread v1;
	private vegetableThread v2;
	private vegetableThread v3;
	
	private itemBoxThread i1;
	private itemBoxThread i2;
	
	private HumanInMole m1, m2, m3, m4, m5, m6, m7, m8, m9;
	
	private boolean humtrap = false;
	private boolean timerstop = false;
	
	public JLabel counterLabel;
	private String ddSecond, ddMinute;
	private DecimalFormat dFormat = new DecimalFormat("00");
	private int second, minute;
	private Timer timer;
	private Font font1 = new Font("Arial", Font.BOLD, 30);
	private Font font2 = new Font("Arial", Font.BOLD, 15);
	private ChannelHandlerContext ctx;
	private String name;
	private int vegcount = 15;
	private int humanlife = 2;
	private int snakesecond = 15;
	private HumanSnake snake;
	private Timer snakeTimer;
	private boolean isSnake = false;
	
	public HumanUI(ChannelHandlerContext ctx, String name, int v1Location, int v2Location, int v3Location, int crop1, int crop2, int crop3) throws IOException {
		this.ctx = ctx;
		this.name = name;
		setLayout(null);
		backImage = ImageIO.read(new File("img/Back4.png"));
		humanHud = ImageIO.read(new File("img/humanHud.png"));
		humanInv = ImageIO.read(new File("img/inventory.png"));
		intHuman = ImageIO.read(new File("img/humanint.png"));
		human = new Human(this, 200, 225, ctx, name);
		add(human);
		
		v1 = new vegetableThread(v1Location, crop1);
		v2 = new vegetableThread(v2Location, crop2);
		v3 = new vegetableThread(v3Location, crop3);
		add(v1);
		add(v2);
		add(v3);
		
		i1 = new itemBoxThread();
		i2 = new itemBoxThread();
		add(i1);
		add(i2);
		i1.setVisible(false);
		i2.setVisible(false);
		
		m1 = new HumanInMole(this, 50, 400);
		m2 = new HumanInMole(this, 100, 400);
		m3 = new HumanInMole(this, 150, 400);
		m4 = new HumanInMole(this ,50, 450);
		m5 = new HumanInMole(this, 100, 450);
		m6 = new HumanInMole(this, 150, 450);
		m7 = new HumanInMole(this, 50, 500);
		m8 = new HumanInMole(this, 100, 500);
		m9 = new HumanInMole(this, 150, 500);
		add(m1);
		add(m2);
		add(m3);
		add(m4);
		add(m5);
		add(m6);
		add(m7);
		add(m8);
		add(m9);
		
		counterLabel = new JLabel("");
		counterLabel.setBounds(345, 0, 100, 50);
		counterLabel.setHorizontalAlignment(JLabel.CENTER);
		counterLabel.setFont(font1);

		add(counterLabel);

		counterLabel.setText("03:00");

		second = 0;
		minute = 3;
		
		normalTimer();
		timer.start();
		
	}
	public vegetableThread getV1() {
		return v1;
	}
	public vegetableThread getV2() {
		return v2;
	}
	public vegetableThread getV3() {
		return v3;
	}
	public itemBoxThread getI1() {
		return i1;
	}
	public itemBoxThread getI2() {
		return i2;
	}
	public Human getHuman() {
		return human;
	}
	public HumanInMole getM1() {
		return m1;
	}
	public HumanInMole getM2() {
		return m2;
	}
	public HumanInMole getM3() {
		return m3;
	}
	public HumanInMole getM4() {
		return m4;
	}
	public HumanInMole getM5() {
		return m5;
	}
	public HumanInMole getM6() {
		return m6;
	}
	public HumanInMole getM7() {
		return m7;
	}
	public HumanInMole getM8() {
		return m8;
	}
	public HumanInMole getM9() {
		return m9;
	}
	public void makeSnake(int x) {
		snake = new HumanSnake(this, x);
		add(snake);
	}
	public void sethumtrap(boolean a) {
        humtrap = a;
    }
    public boolean gethumtrap() {
        return humtrap;
    }
	public boolean getTimerstop() {
		return timerstop;
	}
	public void setTimerstop(boolean timerstop) {
		this.timerstop = timerstop;
	}
	public int getVegcount() {
		return vegcount;
	}
	public void setVegcount(int vegcount) {
		this.vegcount = vegcount;
	}

	
	public void paintComponent(Graphics g) {// �׸��� �Լ�
		super.paintComponent(g);
		g.drawImage(backImage, 0, 0, null);
		g.drawImage(humanHud, 0, 70, null);
		g.drawImage(humanInv, 55, 0, null);
		g.drawImage(intHuman, 0, 0, null);
		
	}
	
	public void moleMessage(String message, int x, int y) {
		if (message.equals("1"))
			m1.humanInMoleMove(message, x, y);
		else if (message.equals("2"))
			m2.humanInMoleMove(message, x, y);
		else if (message.equals("3"))
			m3.humanInMoleMove(message, x, y);
		else if (message.equals("4"))
			m4.humanInMoleMove(message, x, y);
		else if (message.equals("5"))
			m5.humanInMoleMove(message, x, y);
		else if (message.equals("6"))
			m6.humanInMoleMove(message, x, y);
		else if (message.equals("7"))
			m7.humanInMoleMove(message, x, y);
		else if (message.equals("8"))
			m8.humanInMoleMove(message, x, y);
		else if (message.equals("9"))
			m9.humanInMoleMove(message, x, y);
	}
	public void moleDieMessage() {
		if (m1.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 1 + ",");
		else if (m2.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 2 + ",");
		else if (m3.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 3 + ",");
		else if (m4.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 4 + ",");
		else if (m5.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 5 + ",");
		else if (m6.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 6 + ",");
		else if (m7.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 7 + ",");
		else if (m8.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 8 + ",");
		else if (m9.getMoleDeath() == false)
			ctx.writeAndFlush("[MOLEDIE]," + name + "," + 9 + ",");
	}
	public void normalTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				second--;

				ddSecond = dFormat.format(second);
				ddMinute = dFormat.format(minute);

				counterLabel.setText(ddMinute + ":" + ddSecond);

				if (second == -1) {
					second = 59;
					minute--;

					ddSecond = dFormat.format(second);
					ddMinute = dFormat.format(minute);
					counterLabel.setText(ddMinute + ":" + ddSecond);
				}
				if (counterLabel.getText().equals("00:00")) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "�ΰ� �¸�!!", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[HUMANWIN]," + LoginForm.getId());
					setVisible(false);
					if (MoleClientMainHandler.roomTest.testStart.getText().equals("�غ����"))
						MoleClientMainHandler.roomTest.testStart.setText("�غ�");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
				}
				else if (vegcount == 0) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "�δ����� ������ �δ��� ������", "Result", JOptionPane.PLAIN_MESSAGE);
					ctx.writeAndFlush("[HUMANLOSE]," + LoginForm.getId());
					setVisible(false);
					if (MoleClientMainHandler.roomTest.testStart.getText().equals("�غ����"))
						MoleClientMainHandler.roomTest.testStart.setText("�غ�");
					MoleClientMainHandler.roomTest.ready.setText("");
					MoleClientMainHandler.roomTest.setVisible(true);
				}
			}
		});
	}
}


