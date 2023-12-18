import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArticleListDetailComponent } from './article-list-detail.component';

describe('ArticleList Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleListDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ArticleListDetailComponent,
              resolve: { articleList: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ArticleListDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load articleList on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ArticleListDetailComponent);

      // THEN
      expect(instance.articleList).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
