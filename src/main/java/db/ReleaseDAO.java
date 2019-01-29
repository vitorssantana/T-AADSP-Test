package db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.Release;

public class ReleaseDAO {

	private XlsDAO xlsDAO;
	private XSSFWorkbook workbook;
	private XSSFSheet abaRelease;

	public ReleaseDAO() throws IOException {
		xlsDAO = new XlsDAO();
		workbook = xlsDAO.getWorkbook();
		abaRelease = workbook.getSheet("Release");
	}

	public List<Release> retornarListaReleases() {

		List<Release> listaReleases = new ArrayList<Release>();
		Iterator<Row> iterator = abaRelease.iterator();

		Row nextRow;
		if (abaRelease.getRow(1) != null) {
			while (iterator.hasNext()) {
				nextRow = iterator.next();
				if (nextRow.getRowNum() == 0)
					nextRow = iterator.next();

				Iterator<Cell> cellIterator = nextRow.cellIterator();
				cellIterator = nextRow.cellIterator();
				Release release = new Release();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();
					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {

					case 0:
						release.setId((int) nextCell.getNumericCellValue());
						break;

					case 1:
						release.setVersao(nextCell.getStringCellValue());
						break;

					case 2:
						release.setDataEntrega(nextCell.getStringCellValue());
						break;

					}

				}
				listaReleases.add(release);
			}
		}

		return listaReleases;
	}

	public void addNewRelease(Release release) {
		Iterator<Row> iterator = abaRelease.iterator();
		int count = 0;

		while (abaRelease.getRow(count) != null) {
			count++;
		}

		Row row = abaRelease.createRow(count);

		Cell cell = row.createCell(0);
		cell.setCellValue(count);

		cell = row.createCell(1);
		cell.setCellValue(release.getVersao());

		cell = row.createCell(2);
		cell.setCellValue(release.getDataEntrega());

		xlsDAO.writeAndCloseXls();
	}

}
