/*
 * (c) Copyright 2007 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package dev;

import arq.sparql;
import arq.cmd.QueryCmdUtils;
import arq.cmd.ResultsFormat;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.query.core.DataSourceImpl;
import com.hp.hpl.jena.query.engine.QueryEngineBase;
import com.hp.hpl.jena.query.engine2.QueryEngineRef;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;

import engine3.QueryEngineX;

public class Run
{
    public static void main(String[] argv)
    {
        String qs1 = "PREFIX : <http://example/>\n" ;
        String qs = qs1+"SELECT * {  ?s :q ?o GRAPH ?g {  }  }" ;
        Model data = FileManager.get().loadModel("D.ttl") ;
        DataSource ds = new DataSourceImpl() ;
        ds.setDefaultModel(data) ;
        ds.addNamedModel("http://example/g", data) ;
        
        Query query = QueryFactory.create(qs) ;

        if ( false )
        {
            QueryEngineRef.register() ;
            QueryExecution qExec = QueryExecutionFactory.create(query, ds) ;
            System.out.print(((QueryEngineBase)qExec).getPlan()) ;
            QueryCmdUtils.executeQuery(query, qExec, ResultsFormat.FMT_RS_TEXT) ;
            QueryEngineRef.unregister() ;
        }

        execQuery("D.ttl", "Q.rq") ;
        
        QueryEngineX.register() ;
        QueryEngineX qe = new QueryEngineX(query) ;
        qe.setDataset(ds) ;
        QueryCmdUtils.executeQuery(query, qe, ResultsFormat.FMT_RS_TEXT) ;
        
    }
    
    private static void execQuery(String datafile, String queryfile)
    {
        QueryEngineX.register() ;
        String a[] = new String[]{
            //"-v",
            //"--engine=ref",
            "--data="+datafile,
            "-query="+queryfile , 
        } ;
        
        sparql.main(a) ;
        System.exit(0) ;
        
    }
}

/*
 * (c) Copyright 2007 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */