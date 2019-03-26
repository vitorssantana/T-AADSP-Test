package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.PlanoTeste;
import model.Projeto;
import model.PlanoTeste;

public class PlanoTesteDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaPlanoTeste;

	public PlanoTesteDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaPlanoTeste = workbook.getSheet("PlanoTeste");
	}

	public List<PlanoTeste> retornarListaPlanoTestes() {

		List<PlanoTeste> listaPlanoTestes = new ArrayList<PlanoTeste>();
		Iterator<Row> iterator = abaPlanoTeste.iterator();
		iterator.next();
		Row nextRow;

		while (iterator.hasNext()) {
			nextRow = iterator.next();
//			if (nextRow.getRowNum() == 0)
//				nextRow = iterator.next();

			Iterator<Cell> cellIterator = nextRow.cellIterator();
			cellIterator = nextRow.cellIterator();
			PlanoTeste planoTeste = new PlanoTeste();

			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				int columnIndex = nextCell.getColumnIndex();

				switch (columnIndex) {

				case 0:
					planoTeste.setId((int) nextCell.getNumericCellValue());
					break;

				case 1:
					planoTeste.setDescricao(nextCell.getStringCellValue());
					break;

				case 2:
					planoTeste.setTipoTeste(nextCell.getStringCellValue());
					break;

				case 3:
					planoTeste.setIdRequisito((int) nextCell.getNumericCellValue());
					break;

				}

			}
			listaPlanoTestes.add(planoTeste);
		}

		return listaPlanoTestes;
	}

	public void addNewPlanoTeste(PlanoTeste planoTeste) {
		Iterator<Row> iterator = abaPlanoTeste.iterator();
		int count = 0;

		while (abaPlanoTeste.getRow(count) != null) {
			count++;
		}

		Row row = abaPlanoTeste.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(planoTeste.getDescricao());

		cell = row.createCell(2);
		cell.setCellValue(planoTeste.getTipoTeste());

		cell = row.createCell(3);
		cell.setCellValue(planoTeste.getIdRequisito());

		xlsDAO.writeAndCloseXls();
	}

	public void editarDadosTeste(PlanoTeste planoTeste) {
		int count = 1;
		while ((int) abaPlanoTeste.getRow(count).getCell(0).getNumericCellValue() != planoTeste.getId()) {
			count++;
		}

		Row row = abaPlanoTeste.getRow(count);

		Cell cell = row.getCell(1);
		cell.setCellValue(planoTeste.getDescricao());
		cell = row.createCell(2);
		cell.setCellValue(planoTeste.getTipoTeste());

		cell = row.createCell(3);
		cell.setCellValue(planoTeste.getIdRequisito());

		xlsDAO.writeAndCloseXls();

	}

	public void removerCasoTeste(PlanoTeste planoTeste) {
		int count = 1;

		while (true) {
			if (abaPlanoTeste.getRow(count) == null) {
				count++;
			} else if ((int) abaPlanoTeste.getRow(count).getCell(0).getNumericCellValue() != planoTeste.getId()) {
				count++;
			} else {
				break;
			}
		}

		Row row = abaPlanoTeste.getRow(count);
		abaPlanoTeste.removeRow(row);

		xlsDAO.writeAndCloseXls();
	}

}
