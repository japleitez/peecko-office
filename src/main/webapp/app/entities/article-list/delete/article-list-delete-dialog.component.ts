import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IArticleList } from '../article-list.model';
import { ArticleListService } from '../service/article-list.service';

@Component({
  standalone: true,
  templateUrl: './article-list-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ArticleListDeleteDialogComponent {
  articleList?: IArticleList;

  constructor(
    protected articleListService: ArticleListService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.articleListService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
