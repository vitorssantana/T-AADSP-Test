package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Bug;

public class BugDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaBug;

	public BugDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaBug = workbook.getSheet("Bugs");
	}

	public List<Bug> retornarListaBugs() {

		List<Bug> listaBugs = new ArrayList<Bug>();
		Iterator<Row> iterator = abaBug.iterator();

		Row nextRow;
		if (abaBug.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Bug bug = new Bug();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						bug.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						bug.setTitulo(nextCell.getStringCellValue());
						break;

					case 2:
						bug.setDescricao(nextCell.getStringCellValue());
						break;

					case 3:
						bug.setIdDesenvolvedor((int) nextCell.getNumericCellValue());
						break;

					case 4:
						bug.setNivelImpacto(nextCell.getStringCellValue());
						break;

					case 5:
						bug.setIdRequisitoSprint((int) nextCell.getNumericCellValue());
						break;
					}

				}
				listaBugs.add(bug);
			}
		}

		return listaBugs;
	}

	public void addNewBug(Bug bug) {
		Iterator<Row> iterator = abaBug.iterator();
		int count = 0;

		while (abaBug.getRow(count) != null) {
			count++;
		}

		Row row = abaBug.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(bug.getTitulo());

		cell = row.createCell(2);
		cell.setCellValue(bug.getDescricao());

		cell = row.createCell(3);
		cell.setCellValue(bug.getIdDesenvolvedor());

		cell = row.createCell(4);
		cell.setCellValue(bug.getNivelImpacto());

		cell = row.createCell(5);
		cell.setCellValue(bug.getIdRequisitoSprint());

		xlsDAO.writeAndCloseXls();
	}

	public void editarBug(Bug bug) {
		int count = 1;
		while ((int) abaBug.getRow(count).getCell(0).getNumericCellValue() != bug.getId()) {
			count++;
		}

		Row row = abaBug.getRow(count);

		Cell cell = row.getCell(1);
		cell.setCellValue(bug.getTitulo());

		cell = row.createCell(2);
		cell.setCellValue(bug.getDescricao());

		cell = row.createCell(3);
		cell.setCellValue(bug.getIdDesenvolvedor());

		cell = row.createCell(4);
		cell.setCellValue(bug.getNivelImpacto());

		cell = row.createCell(5);
		cell.setCellValue(bug.getIdRequisitoSprint());

		xlsDAO.writeAndCloseXls();
	}

	public void removerBug(Bug bug) {
		int count = 1;

		while (true) {
			if (abaBug.getRow(count) == null) {
				count++;
			} else if ((int) abaBug.getRow(count).getCell(0).getNumericCellValue() != bug.getId()) {
				count++;
			} else {
				break;
			}
		}

		Row row = abaBug.getRow(count);
		abaBug.removeRow(row);

		xlsDAO.writeAndCloseXls();
	}

}
