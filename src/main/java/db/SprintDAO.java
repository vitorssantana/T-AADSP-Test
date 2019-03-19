package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Sprint;

public class SprintDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaSprint;

	public SprintDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaSprint = workbook.getSheet("Sprint");
	}

	public List<Sprint> retornarListaSprints() {

		List<Sprint> listaSprints = new ArrayList<Sprint>();
		Iterator<Row> iterator = abaSprint.iterator();

		Row nextRow;
		if (abaSprint.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Sprint sprint = new Sprint();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						sprint.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						sprint.setNome(nextCell.getStringCellValue());
						break;

					case 2:
						sprint.setDataInicio(nextCell.getStringCellValue());
						break;

					case 3:
						sprint.setDataFim(nextCell.getStringCellValue());
						break;

					case 4:
						sprint.setStatus(nextCell.getStringCellValue());
						break;

					}

				}
				listaSprints.add(sprint);
			}
		}

		return listaSprints;
	}

	public void addNewSprint(Sprint sprint) {

		int count = 0;
		while (abaSprint.getRow(count) != null) {
			count++;
		}

		Row row = abaSprint.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(sprint.getNome());

		cell = row.createCell(2);
		cell.setCellValue(sprint.getDataInicio());

		cell = row.createCell(3);
		cell.setCellValue(sprint.getDataFim());

		cell = row.createCell(4);
		cell.setCellValue(sprint.getStatus());

		xlsDAO.writeAndCloseXls();
	}

	public void editarSprint(Sprint sprint) {
		int count = 1;
		while ((int) abaSprint.getRow(count).getCell(0).getNumericCellValue() != sprint.getId()) {
			count++;
		}

		Row row = abaSprint.getRow(count);

		Cell cell = row.getCell(1);
		cell.setCellValue(sprint.getNome());

		cell = row.createCell(2);
		cell.setCellValue(sprint.getDataInicio());
		cell = row.createCell(3);
		cell.setCellValue(sprint.getDataFim());
		xlsDAO.writeAndCloseXls();
	}

	public void finalizarSprint(Sprint sprint) {
		int count = 1;
		while ((int) abaSprint.getRow(count).getCell(0).getNumericCellValue() != sprint.getId()) {
			count++;
		}

		Row row = abaSprint.getRow(count);

		Cell cell = row.createCell(4);
		cell.setCellValue("Finalizada");
		xlsDAO.writeAndCloseXls();
	}

	public void iniciarSprint(Sprint sprint) {
		int count = 1;
		while ((int) abaSprint.getRow(count).getCell(0).getNumericCellValue() != sprint.getId()) {
			count++;
		}

		Row row = abaSprint.getRow(count);

		Cell cell = row.createCell(4);
		cell.setCellValue("Em Andamento");
		xlsDAO.writeAndCloseXls();
	}

	public void removerSprint(Sprint sprint) {
		int count = 1;

		while (true) {
			if (abaSprint.getRow(count) == null) {
				count++;
			} else if ((int) abaSprint.getRow(count).getCell(0).getNumericCellValue() != sprint.getId()) {
				count++;
			} else {
				break;
			}
		}

		Row row = abaSprint.getRow(count);
		abaSprint.removeRow(row);

		xlsDAO.writeAndCloseXls();
	}

}