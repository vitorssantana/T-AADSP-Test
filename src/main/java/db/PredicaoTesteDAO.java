package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Bug;
import model.PredicaoTeste;

public class PredicaoTesteDAO {
	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaPredicaoTeste;

	public PredicaoTesteDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaPredicaoTeste = workbook.getSheet("PredicaoTeste");
	}

	public void addPredicoesTeste(PredicaoTeste predicaoTeste) {
		Iterator<Row> iterator = abaPredicaoTeste.iterator();
		int count = 0;

		while (abaPredicaoTeste.getRow(count) != null) {
			count++;
		}

		Row row = abaPredicaoTeste.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(predicaoTeste.getIdSprint());

		cell = row.createCell(1);
		cell.setCellValue(predicaoTeste.getIdRequisito());

		cell = row.createCell(2);
		cell.setCellValue(predicaoTeste.getIdProjeto());

		cell = row.createCell(3);
		cell.setCellValue(predicaoTeste.getNomeRequisito());

		cell = row.createCell(4);
		cell.setCellValue(predicaoTeste.getNomeProjeto());

		cell = row.createCell(5);
		cell.setCellValue(predicaoTeste.getProbabilidadeAlta());
		cell = row.createCell(6);
		cell.setCellValue(predicaoTeste.getProbabilidadeMedia());
		cell = row.createCell(7);
		cell.setCellValue(predicaoTeste.getProbabilidadeBaixa());

		xlsDAO.writeAndCloseXls();
	}

	public List<PredicaoTeste> retornarListaPredicoesTeste() {
		List<PredicaoTeste> listaPredicaoTeste = new ArrayList<PredicaoTeste>();
		Iterator<Row> iterator = abaPredicaoTeste.iterator();

		Row nextRow;
		if (abaPredicaoTeste.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				PredicaoTeste predicaoteste = new PredicaoTeste();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						predicaoteste.setIdSprint((int) nextCell.getNumericCellValue());
						break;

					case 1:
						predicaoteste.setIdRequisito((int) nextCell.getNumericCellValue());
						break;

					case 2:
						predicaoteste.setIdProjeto((int) nextCell.getNumericCellValue());
						break;

					case 3:
						predicaoteste.setNomeRequisito(nextCell.getStringCellValue());
						break;

					case 4:
						predicaoteste.setProbabilidadeAlta(nextCell.getNumericCellValue());
						break;

					case 5:
						predicaoteste.setProbabilidadeMedia(nextCell.getNumericCellValue());
						break;

					case 6:
						predicaoteste.setProbabilidadeBaixa((int) nextCell.getNumericCellValue());
						break;

					}

				}
				listaPredicaoTeste.add(predicaoteste);
			}
		}

		return listaPredicaoTeste;
	}

}
