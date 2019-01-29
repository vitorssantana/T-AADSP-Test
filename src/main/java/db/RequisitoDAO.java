package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Requisito;
import model.Requisito;

public class RequisitoDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaRequisito;

	public RequisitoDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaRequisito = workbook.getSheet("Requisito");
	}

	public List<Requisito> retornarListarRequisitos() {

		List<Requisito> listaRequisitos = new ArrayList<Requisito>();
		Iterator<Row> iterator = abaRequisito.iterator();

		Row nextRow;
		if (abaRequisito.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Requisito requisito = new Requisito();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						requisito.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						requisito.setTitulo(nextCell.getStringCellValue());
						break;

					case 2:
						requisito.setIdProjeto((int) nextCell.getNumericCellValue());
						break;

					case 3:
						requisito.setNotaPrioridade((int) nextCell.getNumericCellValue());
						break;

					case 4:
						requisito.setIdStakeholder((int) nextCell.getNumericCellValue());
						break;

					}

				}
				listaRequisitos.add(requisito);
			}
		}

		return listaRequisitos;

	}

	public void addNewRequisito(Requisito requisito) {

		Iterator<Row> iterator = abaRequisito.iterator();
		int count = 0;

		while (abaRequisito.getRow(count) != null) {
			count++;
		}
		Row row = abaRequisito.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(requisito.getTitulo());

		cell = row.createCell(2);
		cell.setCellValue(requisito.getIdProjeto());

		cell = row.createCell(3);
		cell.setCellValue(requisito.getNotaPrioridade());

		cell = row.createCell(4);
		cell.setCellValue(requisito.getIdStakeholder());

		xlsDAO.writeAndCloseXls();
	}

}
