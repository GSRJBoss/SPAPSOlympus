package bo.com.spaps.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

@WebServlet("/codeQR")
public class CodeQR extends HttpServlet {

	private static final long serialVersionUID = 4043174030353635909L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String qrtext = request.getParameter("qrtext");
			System.out.println("Texto Parametro: " + qrtext);

			if(!qrtext.isEmpty()){
				
				ByteArrayOutputStream out = QRCode.from(qrtext).to(ImageType.PNG).withSize(200, 200).stream();
				
				response.setContentType("image/png");
		        response.setContentLength(out.size());
		         
		        OutputStream outStream = response.getOutputStream();
		        
		        outStream.write(out.toByteArray());
		 
		        outStream.flush();
		        outStream.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error en QRCode: "+e.getMessage());
		}

	}


}
