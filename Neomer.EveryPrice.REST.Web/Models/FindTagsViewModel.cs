using Neomer.EveryPrice.SDK.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Neomer.EveryPrice.REST.Web.Models
{

    [Serializable]
    public class FindTagsViewModel
	{
        public FindTagsViewModel()
        {

        }

        public FindTagsViewModel(string suggestion)
        {
			Suggestion = suggestion;
        }

		public string Suggestion;

		public IList<TagViewModel> Tags;

    }
}