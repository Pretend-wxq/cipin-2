package cipin;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class highCountServlet
 */
@WebServlet("/highCountServlet")
public class highCountServlet extends HttpServlet {
	public static String[] str;
	public static String fileName;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public highCountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 请求与响应的编码格式
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 接受页面数据
		String num = request.getParameter("k");// 数字
		fileName = request.getParameter("strFileName");// 文件名

		// 文件的上传
		main f = new main();
		long st = System.currentTimeMillis();
		Map<String, Integer> wordsCount = f.count(fileName);
		Map<String, Integer> wordsCount1 = f.SortMap(wordsCount, Integer.parseInt(num));
		long end = System.currentTimeMillis();
		Long time = end - st;
		HttpSession session = request.getSession();
		session.setAttribute("wordsCount", wordsCount1);
		session.setAttribute("time", time);
		response.sendRedirect("easyui/showHigh.jsp");
		// request.getRequestDispatcher("easyui后台/showHigh.jsp").forward(request,
		// response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
