package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.DesenvolvedorRequisitoSprint;

public class DesenvolvedorRequisitoSprintDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaDesenvolvedorRequisitoSprint;

	public DesenvolvedorRequisitoSprintDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaDesenvolvedorRequisitoSprint = workbook.getSheet("Desenvolvedor_Requisito_Sprint");
	}

	public List<DesenvolvedorRequisitoSprint> retornarListaDesenvolvedorRequisitoSprint() {
		List<DesenvolvedorRequisitoSprint> listaDesenvolvedorRequisitoSprintes = new ArrayList<DesenvolvedorRequisitoSprint>();
		Iterator<Row> iterator = abaDesenvolvedorRequisitoSprint.iterator();

		Row nextRow;
		if (abaDesenvolvedorRequisitoSprint.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				DesenvolvedorRequisitoSprint desenvolvedorRequisitoSprint = new DesenvolvedorRequisitoSprint();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						desenvolvedorRequisitoSprint.setIdRequisitoSprint((int) nextCell.getNumericCellValue());
						break;

					case 1:
						desenvolvedorRequisitoSprint.setIdDesenvolvedor((int) nextCell.getNumericCellValue());
						break;

					case 2:
						desenvolvedorRequisitoSprint.setNivelParticipacao((int) nextCell.getNumericCellValue());
						break;

					}
				}
				
				listaDesenvolvedorRequisitoSprintes.add(desenvolvedorRequisitoSprint);
			}
		}

		return listaDesenvolvedorRequisitoSprintes;
	}

	public void addnewDesenvolvedorRequisitoSprint(DesenvolvedorRequisitoSprint desenvolvedorRequisitoSprint) {
		int count = 0;

		while (abaDesenvolvedorRequisitoSprint.getRow(count) != null) {
			count++;
		}

		Row row = abaDesenvolvedorRequisitoSprint.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(desenvolvedorRequisitoSprint.getIdRequisitoSprint());

		cell = row.createCell(1);
		cell.setCellValue(desenvolvedorRequisitoSprint.getIdDesenvolvedor());

		cell = row.createCell(2);
		cell.setCellValue(desenvolvedorRequisitoSprint.getNivelParticipacao());

		xlsDAO.writeAndCloseXls();

	}

	public void removeDesenvolvedorRequisitoSprint(DesenvolvedorRequisitoSprint desenvolvedorRequisitoSprint) {
		int count = 1;

		while (true) {
			if (abaDesenvolvedorRequisitoSprint.getRow(count) == null) {
				count++;
			} else if ((int) abaDesenvolvedorRequisitoSprint.getRow(count).getCell(0)
					.getNumericCellValue() != desenvolvedorRequisitoSprint.getIdRequisitoSprint()
					&& (int) abaDesenvolvedorRequisitoSprint.getRow(count).getCell(1)
							.getNumericCellValue() != desenvolvedorRequisitoSprint.getIdDesenvolvedor()) {
				count++;
			} else {
				break;
			}
		}

		Row row = abaDesenvolvedorRequisitoSprint.getRow(count);
		abaDesenvolvedorRequisitoSprint.removeRow(row);

		xlsDAO.writeAndCloseXls();
	}

}
