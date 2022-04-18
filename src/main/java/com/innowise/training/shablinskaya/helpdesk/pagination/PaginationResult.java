package com.innowise.training.shablinskaya.helpdesk.pagination;


import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PaginationResult<E> {

    private int totalRecords;
    private int currentPage;
    private List<E> resultList;
    private int maxResult;
    private int totalPage;
    private int maxNavigationPage;
    private List<Integer> navigationPages;

    public PaginationResult(Query<E> query, int page, int maxResult, int maxNavigationPage) {
        final int pageIndex = Math.max(page - 1, 0);

        int fromRecordIndex = pageIndex * maxResult;
        int maxRecordIndex = fromRecordIndex + maxResult;

        ScrollableResults resultsScroll = query.scroll(ScrollMode.SCROLL_INSENSITIVE);

        List<E> resultList = new ArrayList<E>();

        boolean hasResult = resultsScroll.first();

        if (hasResult) {
            // Scroll to position:
            hasResult = resultsScroll.scroll(fromRecordIndex);

            if (hasResult) {
                do {
                    E record = (E) resultsScroll.get(0);
                    resultList.add(record);
                } while (resultsScroll.next()
                        && resultsScroll.getRowNumber() >= fromRecordIndex
                        && resultsScroll.getRowNumber() < maxRecordIndex);
            }
            // Go to Last record.
            resultsScroll.last();
        }

        setTotalRecords(resultsScroll.getRowNumber() +1);
        setCurrentPage(pageIndex +1);
        setResultList(resultList);
        setMaxResult(maxResult);

        if(totalRecords % maxResult == 0){
            totalPage = totalRecords/maxResult;
        }else{
            totalPage = (totalRecords/maxResult) +1;
        }

        this.maxNavigationPage = maxNavigationPage;

     resultsScroll.close();

     calcNavigationPages();
    }

    private void calcNavigationPages(){
        this.navigationPages = new ArrayList<Integer>();

        int current = Math.min(currentPage, totalPage);

        int begin = current - maxNavigationPage/2;
        int end = current + maxNavigationPage/2;

        navigationPages.add(1);
        if (begin > 2) {
            // Using for '...'
            navigationPages.add(-1);
        }
        for (int i = begin; i < end; i++) {
            if (i > 1 && i < this.totalPage) {
                navigationPages.add(i);
            }
        }

        if (end < this.totalPage - 2) {
            // Using for '...'
            navigationPages.add(-1);
        }
        // The last page.
        navigationPages.add(this.totalPage);
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getMaxNavigationPage() {
        return maxNavigationPage;
    }

    public void setMaxNavigationPage(int maxNavigationPage) {
        this.maxNavigationPage = maxNavigationPage;
    }

    public List<Integer> getNavigationPages() {
        return navigationPages;
    }

    public void setNavigationPages(List<Integer> navigationPages) {
        this.navigationPages = navigationPages;
    }
}
