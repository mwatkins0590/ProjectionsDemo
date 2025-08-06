package com.chapter.projectionsdemo.projections;

import java.util.UUID;

public interface BookInterfaceProjection {
  UUID getId();
  String getIsbn();
  String getTitle();
  int getNumberOfPages();
  String getPrintedAuthors();
}
