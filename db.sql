CREATE TABLE "edges" ("id" INTEGER   DEFAULT (null) ,"source" VARCHAR DEFAULT (null) ,"target" VARCHAR DEFAULT (null) ,"label" TEXT,"weight" REAL DEFAULT (1) ,"gid" INTEGER NOT NULL  DEFAULT (-1) );
CREATE TABLE "graphs" ("id" INTEGER PRIMARY KEY  NOT NULL , "name" VARCHAR, "edgeFontSize" INTEGER DEFAULT 18, "nodeFontSize" INTEGER DEFAULT 26);
CREATE TABLE "nodes" ("id" INTEGER  DEFAULT (null) ,"label" TEXT NOT NULL ,"x" REAL,"y" REAL,"gid" INTEGER NOT NULL  DEFAULT (-1) );
