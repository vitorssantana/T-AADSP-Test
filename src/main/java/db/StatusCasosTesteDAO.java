package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.StatusCasosTeste;

public class StatusCasosTesteDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaStatusCasosTeste;

	public StatusCasosTesteDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaStatusCasosTeste = workbook.getSheet("Status_Casos_Teste");
	}

	public List<StatusCasosTeste> retornarListaStatusCasosTestes() {

		List<StatusCasosTeste> listaStatusCasosTestes = new ArrayList<StatusCasosTeste>();
		Iterator<Row> iterator = abaStatusCasosTeste.iterator();
		iterator.next();
		Row nextRow;

		while (iterator.hasNext()) {
			nextRow = iterator.next();
//			if (nextRow.getRowNum() == 0)
//				nextRow = iterator.next();

			Iterator<Cell> cellIterator = nextRow.cellIterator();
			cellIterator = nextRow.cellIterator();
			StatusCasosTeste statusCasosTeste = new StatusCasosTeste();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {

				case 0:
					statusCasosTeste.setIdRequisitoSprint((int) nextCell.getNumericCellValue());
					break;

				case 1:
					statusCasosTeste.setIdCasoTeste((int) nextCell.getNumericCellValue());
					break;

				case 2:
					statusCasosTeste.setStatus(nextCell.getStringCellValue());
					break;

				}

			}
			listaStatusCasosTestes.add(statusCasosTeste);
		}

		return listaStatusCasosTestes;
	}

	public void addNewStatusCasosTeste(StatusCasosTeste statusCasosTeste) {
		int count = 0;

		while (abaStatusCasosTeste.getRow(count) != null) {
			count++;
		}

		Row row = abaStatusCasosTeste.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(statusCasosTeste.getIdRequisitoSprint());

		cell = row.createCell(1);
		cell.setCellValue(statusCasosTeste.getIdCasoTeste());

		cell = row.createCell(2);
		cell.setCellValue(statusCasosTeste.getStatus());

		xlsDAO.writeAndCloseXls();
	}

	public void editarNewStatusCasosTeste(StatusCasosTeste statusCasosTeste) {
		int count = 1;
		while ((int) abaStatusCasosTeste.getRow(count).getCell(0).getNumericCellValue() != statusCasosTeste
				.getIdRequisitoSprint()
				|| (int) abaStatusCasosTeste.getRow(count).getCell(1).getNumericCellValue() != statusCasosTeste
						.getIdCasoTeste()) {
			count++;
		}

		Row row = abaStatusCasosTeste.getRow(count);

		Cell cell = row.createCell(2);
		cell.setCellValue(statusCasosTeste.getStatus());

		xlsDAO.writeAndCloseXls();
	}
}
