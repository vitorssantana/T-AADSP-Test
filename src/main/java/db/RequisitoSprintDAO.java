package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Desenvolvedor;
import model.RequisitoSprint;

public class RequisitoSprintDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaRequisitoSprint;

	public RequisitoSprintDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaRequisitoSprint = workbook.getSheet("Requisito_Sprint");
	}

	public List<RequisitoSprint> retornarListaRequisitoSprint() {
		List<RequisitoSprint> listaRequisitoSprintes = new ArrayList<RequisitoSprint>();
		Iterator<Row> iterator = abaRequisitoSprint.iterator();

		Row nextRow;
		if (abaRequisitoSprint.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				RequisitoSprint requisitoSprint = new RequisitoSprint();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						requisitoSprint.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						requisitoSprint.setIdRequisito((int) nextCell.getNumericCellValue());
						break;

					case 2:
						requisitoSprint.setIdSprint((int) nextCell.getNumericCellValue());
						break;

					case 3:
						requisitoSprint.setVinculouDesenvolvedor(Boolean.getBoolean(nextCell.getStringCellValue()));
						break;
					case 4:
						requisitoSprint.setNivelImpactoAlteracoes(nextCell.getStringCellValue());

					}

				}
				listaRequisitoSprintes.add(requisitoSprint);
			}
		}

		return listaRequisitoSprintes;
	}

	public void addnewRequisitoSprint(RequisitoSprint requisitoSprint) {
		int count = 0;

		while (abaRequisitoSprint.getRow(count) != null) {
			count++;
		}

		Row row = abaRequisitoSprint.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(requisitoSprint.getIdRequisito());

		cell = row.createCell(2);
		cell.setCellValue(requisitoSprint.getIdSprint());

		cell = row.createCell(3);
		cell.setCellValue(String.valueOf(requisitoSprint.isVinculouDesenvolvedor()));

		cell = row.createCell(4);
		cell.setCellValue(String.valueOf(requisitoSprint.getNivelImpactoAlteracoes()));

		xlsDAO.writeAndCloseXls();

	}

	public void editarDadosRequisitoSprint(RequisitoSprint requisitoSprint) {
		int count = 1;
		while ((int) abaRequisitoSprint.getRow(count).getCell(0).getNumericCellValue() != requisitoSprint.getId()) {
			count++;
		}

		Row row = abaRequisitoSprint.getRow(count);

		Cell cell = row.getCell(1);
		cell.setCellValue(requisitoSprint.getIdRequisito());

		cell = row.createCell(2);
		cell.setCellValue(requisitoSprint.getIdSprint());
		
		cell = row.createCell(4);
		cell.setCellValue(String.valueOf(requisitoSprint.getNivelImpactoAlteracoes()));

		xlsDAO.writeAndCloseXls();

	}

	public void removeRequisitoSprint(RequisitoSprint requisitoSprint) {
		int count = 1;
		while (true) {
			if (abaRequisitoSprint.getRow(count) == null) {
				count++;
			} else if ((int) abaRequisitoSprint.getRow(count).getCell(0).getNumericCellValue() != requisitoSprint.getId()) {
				count++;
			} else {
				break;
			}
		}
		Row row = abaRequisitoSprint.getRow(count);
		abaRequisitoSprint.removeRow(row);
		
		xlsDAO.writeAndCloseXls();
	}
}
