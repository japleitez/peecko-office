import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IArticleItem } from '../article-item.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../article-item.test-samples';

import { ArticleItemService } from './article-item.service';

const requireRestSample: IArticleItem = {
  ...sampleWithRequiredData,
};

describe('ArticleItem Service', () => {
  let service: ArticleItemService;
  let httpMock: HttpTestingController;
  let expectedResult: IArticleItem | IArticleItem[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ArticleItemService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ArticleItem', () => {
      const articleItem = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(articleItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ArticleItem', () => {
      const articleItem = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(articleItem).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ArticleItem', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ArticleItem', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ArticleItem', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addArticleItemToCollectionIfMissing', () => {
      it('should add a ArticleItem to an empty array', () => {
        const articleItem: IArticleItem = sampleWithRequiredData;
        expectedResult = service.addArticleItemToCollectionIfMissing([], articleItem);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(articleItem);
      });

      it('should not add a ArticleItem to an array that contains it', () => {
        const articleItem: IArticleItem = sampleWithRequiredData;
        const articleItemCollection: IArticleItem[] = [
          {
            ...articleItem,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addArticleItemToCollectionIfMissing(articleItemCollection, articleItem);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ArticleItem to an array that doesn't contain it", () => {
        const articleItem: IArticleItem = sampleWithRequiredData;
        const articleItemCollection: IArticleItem[] = [sampleWithPartialData];
        expectedResult = service.addArticleItemToCollectionIfMissing(articleItemCollection, articleItem);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(articleItem);
      });

      it('should add only unique ArticleItem to an array', () => {
        const articleItemArray: IArticleItem[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const articleItemCollection: IArticleItem[] = [sampleWithRequiredData];
        expectedResult = service.addArticleItemToCollectionIfMissing(articleItemCollection, ...articleItemArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const articleItem: IArticleItem = sampleWithRequiredData;
        const articleItem2: IArticleItem = sampleWithPartialData;
        expectedResult = service.addArticleItemToCollectionIfMissing([], articleItem, articleItem2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(articleItem);
        expect(expectedResult).toContain(articleItem2);
      });

      it('should accept null and undefined values', () => {
        const articleItem: IArticleItem = sampleWithRequiredData;
        expectedResult = service.addArticleItemToCollectionIfMissing([], null, articleItem, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(articleItem);
      });

      it('should return initial array if no ArticleItem is added', () => {
        const articleItemCollection: IArticleItem[] = [sampleWithRequiredData];
        expectedResult = service.addArticleItemToCollectionIfMissing(articleItemCollection, undefined, null);
        expect(expectedResult).toEqual(articleItemCollection);
      });
    });

    describe('compareArticleItem', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareArticleItem(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareArticleItem(entity1, entity2);
        const compareResult2 = service.compareArticleItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareArticleItem(entity1, entity2);
        const compareResult2 = service.compareArticleItem(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareArticleItem(entity1, entity2);
        const compareResult2 = service.compareArticleItem(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
