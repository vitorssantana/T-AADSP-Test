package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Stakeholder;

public class StakeholderDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaStakeholder;

	public StakeholderDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaStakeholder = workbook.getSheet("Stakeholder");
	}

	public List<Stakeholder> retornarListaStakeholders() {

		List<Stakeholder> listaStakeholders = new ArrayList<Stakeholder>();
		Iterator<Row> iterator = abaStakeholder.iterator();

		Row nextRow;
		if (abaStakeholder.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Stakeholder stakeholder = new Stakeholder();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						stakeholder.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						stakeholder.setNome(nextCell.getStringCellValue());
						break;

					case 2:
						stakeholder.setNotaPrioridade((int) nextCell.getNumericCellValue());
						break;

					}

				}
				listaStakeholders.add(stakeholder);
			}
		}

		return listaStakeholders;
	}

	public void addNewStakeholder(Stakeholder stakeholder) {
		Iterator<Row> iterator = abaStakeholder.iterator();
		int count = 0;

		while (abaStakeholder.getRow(count) != null) {
			count++;
		}

		Row row = abaStakeholder.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(stakeholder.getNome());

		cell = row.createCell(2);
		cell.setCellValue(stakeholder.getNotaPrioridade());

		xlsDAO.writeAndCloseXls();
	}
}
