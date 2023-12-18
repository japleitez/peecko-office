import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ArticleItemDetailComponent } from './article-item-detail.component';

describe('ArticleItem Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleItemDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ArticleItemDetailComponent,
              resolve: { articleItem: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ArticleItemDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load articleItem on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ArticleItemDetailComponent);

      // THEN
      expect(instance.articleItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
