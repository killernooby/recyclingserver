//Creating a new connection pool to multiple hosts.
        var cql = require('node-cassandra-cql');
        var client = new cql.Client({hosts: ['localhost'], keyspace: 'recycling'});
        client.execute('SELECT * FROM recycling.recycledMaterials', ['jbay'],
        function(err, result) {
        if (err) console.log('execute failed');
        else console.log('got userid ' + result.rows[0].userID);
  }
);