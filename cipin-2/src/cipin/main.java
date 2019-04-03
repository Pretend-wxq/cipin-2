package cipin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.swing.JFrame;

public class main extends JFrame {

	private static final long serialVersionUID = 1L;
	public String[] wordCount;
	public Map<String, Integer> wordsCount;

	// �ֵ����
	public void sortMapByKeys(Map<String, Integer> Map) {

		Set<Entry<String, Integer>> mapEntries = Map.entrySet();
		LinkedList<Entry<String, Integer>> List = new LinkedList<Entry<String, Integer>>(mapEntries);
		// ����ӳ��ļ����б�����
		Collections.sort(List, new Comparator<Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> ele1, Entry<String, Integer> ele2) {
				return ele1.getKey().compareTo(ele2.getKey());
			}
		});
		Map<String, Integer> Map2 = new LinkedHashMap<String, Integer>();
		for (Entry<String, Integer> entry : List) {
			Map2.put(entry.getKey(), entry.getValue());
		}
		// ������õĵ��ʼ���Ӧ�Ĵ�Ƶ�ŵ��ļ���
		File file = new File("../result.txt");
		try {
			if (file.exists()) {
				file.createNewFile();
			}
			FileWriter fop = new FileWriter(file.getAbsoluteFile());
			for (Entry<String, Integer> entry : Map2.entrySet()) {
				fop.write(entry.getKey() + ":\t" + entry.getValue() + "\n");
			}
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ��������
	public Map<String, Integer> SortMap(Map<String, Integer> oldmap, int n) {

		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldmap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue(); // ����
			}
		});
		Map<String, Integer> Map2 = new TreeMap<String, Integer>();

		for (int i = 0; i < n; i++) {
			Map2.put(list.get(i).getKey(), list.get(i).getValue());
			System.out.println(Map2.get(list.get(i).getKey()));
		}
		return Map2;
	}

	// ��ʾ�û���Ҫ��ѯ�����ɸ����ʵĴ�Ƶ
	public Map<String, Integer> query(Map<String, Integer> Map, String s) {
		Map<String, Integer> Map2 = new TreeMap<String, Integer>();
		String[] word = s.split(",");

		int i;
		for (i = 0; i < word.length; i++) {
			for (Entry<String, Integer> entry : Map.entrySet()) {
				if (word[i].equals(entry.getKey())) {
					Map2.put(entry.getKey(), entry.getValue());
					// System.out.println(entry.getKey() + ":\t " +
					// entry.getValue());
					break;
				}

			}
		}
		main demo = new main(word, Map2);
		demo.setVisible(true);
		return Map2;

	}

	// ��״ͼ
	public main(String[] wordCount, Map<String, Integer> wordsCount) {
		super();
		this.wordCount = wordCount;
		this.wordsCount = wordsCount;

		setTitle("��������ͼ");
		setBounds(wordCount.length, 200, 450, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {
		int Width = getWidth();
		int Height = getHeight();
		int leftMargin = 50;// ����ͼ��߽�
		int topMargin = 50;// ����ͼ�ϱ߽�
		Graphics2D g2 = (Graphics2D) g;
		int ruler = Height - topMargin;
		int rulerStep = ruler / 20;// ����ǰ�ĸ߶�����Ϊ20����λ
		g2.setColor(Color.WHITE);// ���ð�ɫ����
		g2.fillRect(0, 0, Width, Height);// ���ƾ���ͼ
		g2.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < rulerStep; i++) {
			g2.drawString((400 - 20 * i) + "��", 8, topMargin + rulerStep * i);// ����Y���ϵ�����
		}
		g2.setColor(Color.BLUE);
		int m = 0;
		for (int i = 0; i < wordCount.length; i++) {
			int value = wordsCount.get(wordCount[i]);
			int step = (m + 1) * 40;// ����ÿ������ͼ��ˮƽ���Ϊ40
			g2.fillRoundRect(leftMargin + step * 2, Height - value, 40, value, 0, 0);// ����ÿ����״��
			g2.drawString(wordCount[i], leftMargin + step * 2, Height - value - 5); // ��ʶÿ����״��
			m++;
		}
	}

	public Map<String, Integer> allCount(String fileName) throws IOException {
		Map<String, Integer> Map2 = new TreeMap<String, Integer>();
		String line = fileName;
		File file = new File(line);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		int charNum = 0;// �ַ���
		int wordsNum = 0;// ������
		int lineNum = 0;// ����
		// ��������ʽ�����ļ�
		while (br.read() != -1) {
			String s = br.readLine();
			charNum += s.length();
			wordsNum += s.split(" ").length;
			lineNum++;
		}
		isr.close();// �ر�
		Map2.put("�ַ���", charNum);
		Map2.put("������", wordsNum);
		Map2.put("����", lineNum);
		return Map2;
	}

	public Map<String, Integer> count(String fileName) throws ServletException, IOException {
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		String line = fileName;
		File file = new File(line);
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(isr);
		// ���ı��е�Ӣ�Ĵ�����ڼ�������
		while ((line = reader.readLine()) != null) {
			line = line.toLowerCase();// ���Դ�Сд
			String[] str = line.split("[^a-zA-Z]");// ���˳�ֻ������ĸ��
			for (int i = 0; i < str.length; i++) {
				String word = str[i].trim();
				if (map.containsKey(word) && word.length() != 0) {
					map.put(word, map.get(word) + 1);
				} else {
					map.put(word, 1);
				}
			}
		}
		return map;
	}

	public main() {

	}

}
